package com.example.wikiapp.ui.screens.newpage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.wikiapp.R
import com.example.wikiapp.ui.components.MDDocument
import com.example.wikiapp.ui.components.appbars.WikiAppBar
import com.example.wikiapp.ui.components.button.ButtonWithImageText
import com.example.wikiapp.ui.components.dialogs.InfoAboutEditorDialog
import com.example.wikiapp.ui.components.dialogs.SettingsOfPageDialog
import com.example.wikiapp.ui.components.dialogs.YesNoDialog
import com.example.wikiapp.ui.components.dropdownmenu.DropDownItem
import com.example.wikiapp.ui.components.fabs.ScrollToTopFab
import com.example.wikiapp.ui.components.webview.HtmlContentView
import com.example.wikiapp.ui.theme.*
import com.example.wikiapp.utils.subscribeOnError
import kotlinx.coroutines.launch
import org.commonmark.node.Document
import org.commonmark.parser.Parser

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewPage(
    navController: NavController,
    showMessage: (message: Int) -> Unit = {}
) {
    val titleEditorHtml = "html"
    val titleEditorMarkdown = "markdown"
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scrollState = remember { ScrollState(0) }
    val viewModel: NewPageViewModel = viewModel()
    val screenScope = LocalLifecycleOwner.current
    var showWebView by remember { mutableStateOf(false) }
    val getUsersProfileResult by viewModel.getUsersProfileResult.collectAsState()
    getUsersProfileResult.subscribeOnError(showMessage)
    val createNewPageResult by viewModel.createNewPageResult.collectAsState()
    createNewPageResult.subscribeOnError(showMessage)

    val listOfStartSymbolsForHeaders = listOf("", "# ", "## ", "### ", "#### ", "##### ", "###### ")
    val listOfEndSymbolsForQuotes =
        listOf("", "{}", "{.is-info}", "{.is-success}", "{.is-warning}", "{.is-danger}")

    val toastToSelectText = "Сначала выделите текст"
    val toastNotToSelectText = "Поставьте курсор на нужную строку"
    val toastToAddTagsOl = "При необходимости добавьте теги <ol> и </ol> в начале и конце списка"
    val toastToAddTagsUl = "При необходимости добавьте теги <ul> и </ul> в начале и конце списка"
    val toastPageIsCreated = "Страница создана"
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }

    val scrollStateVer = rememberScrollState()
    var expandedT by remember { mutableStateOf(false) }
    var expandedH by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    val focusRequester = remember { FocusRequester() }
    val quote = "quote"
    val header = "header"
    val titlesOfHeaders = listOf(
        "", "Заголовок 1", "Заголовок 2", "Заголовок 3", "Заголовок 4",
        "Заголовок 5", "Заголовок 6"
    )
    val titleQuote = "Цитата"
    val titleQuoteInfo = "Цитата информационная"
    val titleQuoteBlock = "Цитата блок"
    val titleQuoteSuccess = "Цитата успешно"
    val titleQuoteWarning = "Цитата предупреждение"
    val titleQuoteError = "Цитата ошибка"

    val titleClearEditor = "Очистить редактор?"
    val subTitleClearEditor = "Вы действительно хотите удалить все содержимое страницы?"
    val titleCreatePage = "Создать страницу?"
    val subTitleCreatePage = "Вы действительно хотите создать страницу?"

    fun showToast(message: String) {
        scope.launch {
            snackbarHostState.value.showSnackbar(message)
        }
    }

    val showInfoAboutEditorDialog = remember { mutableStateOf(false) }
    if (showInfoAboutEditorDialog.value) {
        InfoAboutEditorDialog(
            editor = viewModel.dEditor,
            setShowDialog = { showInfoAboutEditorDialog.value = it })
    }

    val showSettingsOfPageDialog = remember { mutableStateOf(false) }
    if (showSettingsOfPageDialog.value) {
        SettingsOfPageDialog(setShowDialog = { showSettingsOfPageDialog.value = it })
    }

    val isClearPageDialog = remember { mutableStateOf(false) }
    if (isClearPageDialog.value) {
        YesNoDialog(
            title = titleClearEditor,
            subTitle = subTitleClearEditor,
            setShowDialog = { isClearPageDialog.value = it },
            onClick = {
                textFieldValue = TextFieldValue("")
                isClearPageDialog.value = false
            })
    }

    val isCreatePageDialog = remember { mutableStateOf(false) }
    if (isCreatePageDialog.value) {
        YesNoDialog(
            title = titleCreatePage,
            subTitle = subTitleCreatePage,
            setShowDialog = { isCreatePageDialog.value = it },
            onClick = {
                viewModel.createNewPage(
                    content = textFieldValue.text,
                    description = viewModel.dDescription.ifEmpty { "" },
                    editor = viewModel.dEditor,
                    isPublished = viewModel.dIsPublished,
                    isPrivate = viewModel.dIsPrivate,
                    locale = viewModel.dLanguage,
                    path = viewModel.dPath.ifEmpty { "test/test/useless" },
                    tags = if (viewModel.dTags.isNotEmpty())
                        viewModel.dTags.split(',') else
                        listOf(""),
                    title = viewModel.dTitle.ifEmpty { "Title" }
                )
                showToast(toastPageIsCreated)
                isCreatePageDialog.value = false
            })
    }

    val selectionStart = textFieldValue.selection.start
    val selectionEnd = textFieldValue.selection.end

    fun getSelectedText(): String {
        var selectedText = ""
        if (selectionStart != selectionEnd) {
            selectedText = if (selectionStart < selectionEnd) {
                textFieldValue.text.substring(
                    selectionStart,
                    selectionEnd
                )
            } else {
                textFieldValue.text.substring(
                    selectionEnd,
                    selectionStart
                )
            }
        }
        return selectedText
    }

    fun addSymbolsToSelectedText(symbolBefore: String, symbolAfter: String): String {
        return (textFieldValue.text.substring(0, selectionStart)
                + symbolBefore + getSelectedText() + symbolAfter + " " + textFieldValue.text.substring(
            selectionEnd
        ))
    }

    fun getLine(textFieldValue: TextFieldValue): Int {
        val text = textFieldValue.text
        val selection = textFieldValue.selection.start
        val lineList = mutableListOf<Int>()
        text.forEachIndexed { index: Int, c: Char ->
            if (c == '\n') {
                lineList.add(index)
            }
        }
        if (lineList.isEmpty()) return 0

        lineList.forEachIndexed { index, lineEndIndex ->
            if (selection <= lineEndIndex) {
                return index
            }
        }
        return lineList.size
    }

    fun changeTypeOfHeaderInLine(item: String, symbol: String): String {
        var changedLine = ""
        when {
            item.startsWith(listOfStartSymbolsForHeaders[1]) -> {
                changedLine = item.replaceRange(
                    IntRange(0, listOfStartSymbolsForHeaders[1].length - 1),
                    symbol
                )
            }
            item.startsWith(listOfStartSymbolsForHeaders[2]) -> {
                changedLine = item.replaceRange(
                    IntRange(0, listOfStartSymbolsForHeaders[2].length - 1),
                    symbol
                )
            }
            item.startsWith(listOfStartSymbolsForHeaders[3]) -> {
                changedLine = item.replaceRange(
                    IntRange(0, listOfStartSymbolsForHeaders[3].length - 1),
                    symbol
                )
            }
            item.startsWith(listOfStartSymbolsForHeaders[4]) -> {
                changedLine = item.replaceRange(
                    IntRange(0, listOfStartSymbolsForHeaders[4].length - 1),
                    symbol
                )
            }
            item.startsWith(listOfStartSymbolsForHeaders[5]) -> {
                changedLine = item.replaceRange(
                    IntRange(0, listOfStartSymbolsForHeaders[5].length - 1),
                    symbol
                )
            }
            item.startsWith(listOfStartSymbolsForHeaders[6]) -> {
                changedLine = item.replaceRange(
                    IntRange(0, listOfStartSymbolsForHeaders[6].length - 1),
                    symbol
                )
            }
        }
        return changedLine
    }

    fun changeTypeOfQuote(item: String, symbolEnd: String): String {
        var changedLine = ""
        when {
            item.endsWith(listOfEndSymbolsForQuotes[1]) -> {
                changedLine = item.replaceRange(
                    IntRange(item.length - 1 - symbolEnd.length, item.length - 1),
                    symbolEnd
                )
            }
            item.endsWith(listOfEndSymbolsForQuotes[2]) -> {
                changedLine = item.replaceRange(
                    IntRange(item.length - 1 - symbolEnd.length, item.length - 1),
                    symbolEnd
                )
            }
            item.endsWith(listOfEndSymbolsForQuotes[3]) -> {
                changedLine = item.replaceRange(
                    IntRange(item.length - 1 - symbolEnd.length, item.length - 1),
                    symbolEnd
                )
            }
            item.endsWith(listOfEndSymbolsForQuotes[4]) -> {
                changedLine = item.replaceRange(
                    IntRange(item.length - 1 - symbolEnd.length, item.length - 1),
                    symbolEnd
                )
            }
            item.endsWith(listOfEndSymbolsForQuotes[5]) -> {
                changedLine = item.replaceRange(
                    IntRange(item.length - 1 - symbolEnd.length, item.length - 1),
                    symbolEnd
                )
            }
        }
        return changedLine
    }

    fun addSymbolToStartOfLine(
        newText: String,
        symbolStart: String,
        symbolEnd: String,
        item: String
    ): String {
        var new = newText
        new += symbolStart + item + symbolEnd
        if (item.isNotEmpty())
            item.replaceRange(
                0,
                item.lastIndex,
                symbolStart + item + symbolEnd
            )
        else item.plus(symbolStart + symbolEnd)
        return new
    }

    fun addSymbolAtStartOfLine(
        type: String,
        text: TextFieldValue,
        symbolStart: String,
        symbolEnd: String
    ): TextFieldValue {
        val selection = text.selection
        val line = getLine(text)
        val lines = text.text.split("\n")
        var newText = ""

        if (selection.start == selection.end) { // selection is empty
            if (lines.isEmpty()) {
                newText += symbolStart + symbolEnd //"\n"
            } else {
                when (type) {
                    "header" -> {
                        lines.forEachIndexed { index, item ->
                            if (index != line) {
                                newText += item + "\n"
                            } else {
                                if (item.startsWith(
                                        listOfStartSymbolsForHeaders[1]
                                    ) ||

                                    item.startsWith(
                                        listOfStartSymbolsForHeaders[2]
                                    ) ||
                                    item.startsWith(listOfStartSymbolsForHeaders[3]) || item.startsWith(
                                        listOfStartSymbolsForHeaders[4]
                                    )
                                    || item.startsWith(listOfStartSymbolsForHeaders[5]) || item.startsWith(
                                        listOfStartSymbolsForHeaders[6]
                                    )
                                ) {
                                    newText += item.replaceRange(
                                        0,
                                        item.length - 1,
                                        changeTypeOfHeaderInLine(item, symbolStart)
                                    )
                                } else
                                    newText = addSymbolToStartOfLine(
                                        newText,
                                        symbolStart,
                                        symbolEnd,
                                        item
                                    )
                            }
                        }
                    }
                    "quote" -> {
                        lines.forEachIndexed { index, item ->
                            if (index != line) {
                                newText += item + "\n"
                            } else {
                                if
                                        (
                                    item.endsWith(listOfEndSymbolsForQuotes[1])
                                    ||
                                    item.endsWith(listOfEndSymbolsForQuotes[2])

                                    ||
                                    item.endsWith(listOfEndSymbolsForQuotes[3])
                                    ||

                                    item.endsWith(
                                        listOfEndSymbolsForQuotes[4]
                                    )
                                    || item.endsWith(listOfEndSymbolsForQuotes[5])
                                ) {
                                    newText += item.replaceRange(
                                        0,
                                        item.length - 1,
                                        changeTypeOfQuote(item, symbolEnd)
                                    )
                                } else
                                    newText = addSymbolToStartOfLine(
                                        newText,
                                        symbolStart,
                                        symbolEnd,
                                        item
                                    )
                            }
                        }
                    }
                }

            }
            return TextFieldValue(newText)
        }
        return text
    }

    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    Scaffold(
        topBar = {
            WikiAppBar(
                userName = getUsersProfileResult.data?.data?.users?.profile?.name ?: "",
                userEmail = getUsersProfileResult.data?.data?.users?.profile?.email ?: "",
                logoutOnClick = {
                    screenScope.lifecycleScope.launch {
                        viewModel.changeLocation("https://wiki.miem.hse.ru/logout")
                        viewModel.changeToken("")
                        showWebView = true
                    }
                },
                navController = navController,
            )
        },
        floatingActionButton = {
            ScrollToTopFab(scrollState = scrollState)
        },
        scaffoldState = scaffoldState,
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .background(if (viewModel.dEditor == titleEditorMarkdown) backgroundBlue else darkGreenColor)
                    .fillMaxHeight()
                    .width(35.dp)
                    .weight(1F)
                    .verticalScroll(scrollStateVer)
            ) {

                IconButton(onClick = {
                    if (getSelectedText().isNotEmpty()) {
                        textFieldValue = TextFieldValue(
                            text =
                            when (viewModel.dEditor) {
                                titleEditorMarkdown -> addSymbolsToSelectedText("**", "**")
                                titleEditorHtml -> addSymbolsToSelectedText("<b>", "</b>")
                                else -> {
                                    ""
                                }
                            },
                            selection = textFieldValue.selection
                        )
                    } else {
                        showToast(message = toastToSelectText)
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.format_bold),
                        contentDescription = "Bold",
                        tint = Color.White
                    )
                }

                IconButton(onClick = {
                    if (getSelectedText().isNotEmpty()) {
                        textFieldValue = TextFieldValue(
                            text =
                            when (viewModel.dEditor) {
                                titleEditorMarkdown -> addSymbolsToSelectedText("*", "*")
                                titleEditorHtml -> addSymbolsToSelectedText("<i>", "</i>")
                                else -> {
                                    ""
                                }
                            },
                            selection = textFieldValue.selection
                        )
                    } else {
                        showToast(message = toastToSelectText)
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.format_italic),
                        contentDescription = "Italic",
                        tint = Color.White
                    )
                }

                IconButton(onClick = {
                    if (getSelectedText().isNotEmpty()) {
                        textFieldValue = TextFieldValue(
                            text =
                            when (viewModel.dEditor) {
                                titleEditorMarkdown -> addSymbolsToSelectedText("~~", "~~")
                                titleEditorHtml -> addSymbolsToSelectedText("<s>", "</s>")
                                else -> {
                                    ""
                                }
                            },
                            selection = textFieldValue.selection
                        )
                    } else {
                        showToast(message = toastToSelectText)
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.format_strikethrough),
                        contentDescription = "T",
                        tint = Color.White
                    )
                }

                Box {
                    IconButton(onClick = {
                        expandedH = true
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.format_header_pound),
                            contentDescription = "Header",
                            tint = Color.White
                        )
                    }
                    DropdownMenu(
                        expanded = expandedH,
                        onDismissRequest = { expandedH = false }
                    ) {
                        DropDownItem(
                            onClick = {
                                if (getSelectedText().isEmpty()) {
                                    textFieldValue = addSymbolAtStartOfLine(
                                        header,
                                        textFieldValue,
                                        when (viewModel.dEditor) {
                                            titleEditorMarkdown -> listOfStartSymbolsForHeaders[1]
                                            titleEditorHtml -> "<h1>"
                                            else -> {
                                                ""
                                            }
                                        },
                                        when (viewModel.dEditor) {
                                            titleEditorMarkdown -> "\n"
                                            titleEditorHtml -> "</h1>"
                                            else -> {
                                                ""
                                            }
                                        }
                                    )
                                    focusRequester.requestFocus()
                                } else {
                                    showToast(toastNotToSelectText)
                                }
                            },
                            imageId = R.drawable.format_header_1,
                            imageWidth = 24.dp,
                            imageHeight = 24.dp,
                            colorOfImage = Color.Gray,
                            textOfItem = titlesOfHeaders[1]
                        )
                        Divider()
                        DropDownItem(
                            onClick = {
                                if (getSelectedText().isEmpty()) {
                                    textFieldValue = addSymbolAtStartOfLine(
                                        header,
                                        textFieldValue,
                                        when (viewModel.dEditor) {
                                            titleEditorMarkdown -> listOfStartSymbolsForHeaders[2]
                                            titleEditorHtml -> "<h2>"
                                            else -> {
                                                ""
                                            }
                                        },
                                        when (viewModel.dEditor) {
                                            titleEditorMarkdown -> "\n"
                                            titleEditorHtml -> "</h2>"
                                            else -> {
                                                ""
                                            }
                                        }
                                    )
                                    focusRequester.requestFocus()
                                } else {
                                    showToast(toastNotToSelectText)
                                }
                            },
                            imageId = R.drawable.format_header_2,
                            imageWidth = 22.dp,
                            imageHeight = 22.dp,
                            colorOfImage = Color.Gray,
                            textOfItem = titlesOfHeaders[2]
                        )
                        Divider()
                        DropDownItem(
                            onClick = {
                                if (getSelectedText().isEmpty()) {
                                    textFieldValue = addSymbolAtStartOfLine(
                                        header,
                                        textFieldValue,
                                        when (viewModel.dEditor) {
                                            titleEditorMarkdown -> listOfStartSymbolsForHeaders[3]
                                            titleEditorHtml -> "<h3>"
                                            else -> {
                                                ""
                                            }
                                        },
                                        when (viewModel.dEditor) {
                                            titleEditorMarkdown -> "\n"
                                            titleEditorHtml -> "</h3>"
                                            else -> {
                                                ""
                                            }
                                        }
                                    )
                                    focusRequester.requestFocus()
                                } else {
                                    showToast(toastNotToSelectText)
                                }
                            },
                            imageId = R.drawable.format_header_3,
                            imageWidth = 20.dp,
                            imageHeight = 20.dp,
                            colorOfImage = Color.Gray,
                            textOfItem = titlesOfHeaders[3]
                        )
                        Divider()
                        DropDownItem(
                            onClick = {
                                if (getSelectedText().isEmpty()) {
                                    textFieldValue = addSymbolAtStartOfLine(
                                        header,
                                        textFieldValue,
                                        when (viewModel.dEditor) {
                                            titleEditorMarkdown -> listOfStartSymbolsForHeaders[4]
                                            titleEditorHtml -> "<h4>"
                                            else -> {
                                                ""
                                            }
                                        },
                                        when (viewModel.dEditor) {
                                            titleEditorMarkdown -> "\n"
                                            titleEditorHtml -> "</h4>"
                                            else -> {
                                                ""
                                            }
                                        }
                                    )
                                    focusRequester.requestFocus()
                                } else {
                                    showToast(toastNotToSelectText)
                                }
                            },
                            imageId = R.drawable.format_header_4,
                            imageWidth = 18.dp,
                            imageHeight = 18.dp,
                            colorOfImage = Color.Gray,
                            textOfItem = titlesOfHeaders[4]
                        )
                        Divider()
                        DropDownItem(
                            onClick = {
                                if (getSelectedText().isEmpty()) {
                                    textFieldValue = addSymbolAtStartOfLine(
                                        header,
                                        textFieldValue,
                                        when (viewModel.dEditor) {
                                            titleEditorMarkdown -> listOfStartSymbolsForHeaders[5]
                                            titleEditorHtml -> "<h5>"
                                            else -> {
                                                ""
                                            }
                                        },
                                        when (viewModel.dEditor) {
                                            titleEditorMarkdown -> "\n"
                                            titleEditorHtml -> "</h5>"
                                            else -> {
                                                ""
                                            }
                                        }
                                    )
                                    focusRequester.requestFocus()
                                } else {
                                    showToast(toastNotToSelectText)
                                }
                            },
                            imageId = R.drawable.format_header_5,
                            imageWidth = 16.dp,
                            imageHeight = 16.dp,
                            colorOfImage = Color.Gray,
                            textOfItem = titlesOfHeaders[5]
                        )
                        Divider()
                        DropDownItem(
                            onClick = {
                                if (getSelectedText().isEmpty()) {
                                    textFieldValue =
                                        addSymbolAtStartOfLine(
                                            header,
                                            textFieldValue,
                                            when (viewModel.dEditor) {
                                                titleEditorMarkdown -> listOfStartSymbolsForHeaders[6]
                                                titleEditorHtml -> "<h6>"
                                                else -> {
                                                    ""
                                                }
                                            },
                                            when (viewModel.dEditor) {
                                                titleEditorMarkdown -> "\n"
                                                titleEditorHtml -> "</h6>"
                                                else -> {
                                                    ""
                                                }
                                            }
                                        )
                                    focusRequester.requestFocus()
                                } else {
                                    showToast(toastNotToSelectText)
                                }
                            },
                            imageId = R.drawable.format_header_6,
                            imageWidth = 14.dp,
                            imageHeight = 14.dp,
                            colorOfImage = Color.Gray,
                            textOfItem = titlesOfHeaders[6]
                        )
                    }
                }

                IconButton(onClick = {
                    if (getSelectedText().isNotEmpty()) {
                        textFieldValue = TextFieldValue(
                            text =
                            when (viewModel.dEditor) {
                                titleEditorMarkdown -> addSymbolsToSelectedText("~", "~")
                                titleEditorHtml -> addSymbolsToSelectedText("<sub>", "</sub>")
                                else -> {
                                    ""
                                }
                            },
                            selection = textFieldValue.selection
                        )
                    } else {
                        showToast(message = toastToSelectText)
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.format_subscript),
                        contentDescription = "Subscript",
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    if (getSelectedText().isNotEmpty()) {
                        textFieldValue = TextFieldValue(
                            text =
                            when (viewModel.dEditor) {
                                titleEditorMarkdown -> addSymbolsToSelectedText("^", "^")
                                titleEditorHtml -> addSymbolsToSelectedText("<sup>", "</sup>")
                                else -> {
                                    ""
                                }
                            },
                            selection = textFieldValue.selection
                        )
                    } else {
                        showToast(message = toastToSelectText)
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.format_superscript),
                        contentDescription = "Superscript",
                        tint = Color.White
                    )
                }

                Box {
                    IconButton(onClick = {
                        expandedT = true
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.alpha_t_box_outline),
                            contentDescription = "TBox",
                            tint = Color.White
                        )
                    }
                    DropdownMenu(
                        expanded = expandedT,
                        onDismissRequest = { expandedT = false }
                    ) {
                        DropDownItem(
                            onClick = {
                                if (getSelectedText().isEmpty()) {
                                    textFieldValue =
                                        when (viewModel.dEditor) {
                                            titleEditorMarkdown -> addSymbolAtStartOfLine(
                                                quote, textFieldValue,
                                                "> ", "\n"
                                            )
                                            titleEditorHtml -> addSymbolAtStartOfLine(
                                                quote, textFieldValue,
                                                "<q>", "</q>"
                                            )
                                            else -> addSymbolAtStartOfLine(
                                                quote, textFieldValue,
                                                "", ""
                                            )
                                        }
                                    focusRequester.requestFocus()
                                } else {
                                    showToast(toastNotToSelectText)
                                }
                            },
                            imageId = R.drawable.alpha_t_box_outline,
                            imageWidth = 24.dp,
                            imageHeight = 24.dp,
                            colorOfImage = Color.Gray,
                            textOfItem = titleQuote
                        )
                        Divider()
                        DropDownItem(
                            onClick = {
                                if (getSelectedText().isEmpty()) {
                                    textFieldValue =
                                        when (viewModel.dEditor) {
                                            titleEditorMarkdown -> addSymbolAtStartOfLine(
                                                quote,
                                                textFieldValue,
                                                "> ",
                                                "\n{.is-info}"
                                            )
                                            titleEditorHtml -> addSymbolAtStartOfLine(
                                                quote, textFieldValue,
                                                "<blockquote>", "</blockquote>"
                                            )
                                            else -> addSymbolAtStartOfLine(
                                                quote, textFieldValue,
                                                "", ""
                                            )
                                        }
                                    focusRequester.requestFocus()
                                } else {
                                    showToast(toastNotToSelectText)
                                }
                            },
                            imageId = R.drawable.alpha_i_box_outline,
                            imageWidth = 24.dp,
                            imageHeight = 24.dp,
                            colorOfImage = Color.Blue,
                            textOfItem = when (viewModel.dEditor) {
                                titleEditorMarkdown -> titleQuoteInfo
                                titleEditorHtml -> titleQuoteBlock
                                else -> ""
                            }
                        )
                        when (viewModel.dEditor) {
                            titleEditorMarkdown -> Divider()
                            titleEditorHtml -> ""
                            else -> ""
                        }
                        when (viewModel.dEditor) {
                            titleEditorMarkdown -> {
                                DropDownItem(
                                    onClick = {
                                        if (getSelectedText().isEmpty()) {
                                            textFieldValue = addSymbolAtStartOfLine(
                                                quote,
                                                textFieldValue,
                                                "> ",
                                                "\n{.is-success}"
                                            )
                                            focusRequester.requestFocus()
                                        } else {
                                            showToast(toastNotToSelectText)
                                        }
                                    },
                                    imageId = R.drawable.alpha_s_box_outline,
                                    imageWidth = 24.dp,
                                    imageHeight = 24.dp,
                                    colorOfImage = darkGreenColor,
                                    textOfItem = titleQuoteSuccess
                                )
                                Divider()
                            }
                            titleEditorHtml -> ""
                            else -> ""
                        }

                        when (viewModel.dEditor) {
                            titleEditorMarkdown -> {
                                DropDownItem(
                                    onClick = {
                                        if (getSelectedText().isEmpty()) {
                                            textFieldValue = addSymbolAtStartOfLine(
                                                quote,
                                                textFieldValue,
                                                "> ",
                                                "\n{.is-warning}"
                                            )
                                            focusRequester.requestFocus()
                                        } else {
                                            showToast(toastNotToSelectText)
                                        }
                                    },
                                    imageId = R.drawable.alpha_w_box_outline,
                                    imageWidth = 24.dp,
                                    imageHeight = 24.dp,
                                    colorOfImage = darkYellowColor,
                                    textOfItem = titleQuoteWarning
                                )
                                Divider()
                            }
                            titleEditorHtml -> ""
                            else -> ""
                        }
                        when (viewModel.dEditor) {
                            titleEditorMarkdown -> {

                                DropDownItem(
                                    onClick = {
                                        if (getSelectedText().isEmpty()) {
                                            textFieldValue = addSymbolAtStartOfLine(
                                                quote,
                                                textFieldValue,
                                                "> ",
                                                "\n{.is-danger}"
                                            )
                                            focusRequester.requestFocus()
                                        } else {
                                            showToast(toastNotToSelectText)
                                        }
                                    },
                                    imageId = R.drawable.alpha_e_box_outline,
                                    imageWidth = 24.dp,
                                    imageHeight = 24.dp,
                                    colorOfImage = darkRedColor,
                                    textOfItem = titleQuoteError
                                )
                            }
                            titleEditorHtml -> ""
                            else -> ""
                        }
                    }
                }

                IconButton(onClick = {
                    if (getSelectedText().isEmpty()) {
                        textFieldValue =
                            when (viewModel.dEditor) {
                                titleEditorMarkdown -> addSymbolAtStartOfLine(
                                    header,
                                    textFieldValue,
                                    "- ",
                                    "\n"
                                )
                                titleEditorHtml -> addSymbolAtStartOfLine(
                                    header, textFieldValue,
                                    "<li>", ""
                                )
                                else -> addSymbolAtStartOfLine(
                                    header, textFieldValue,
                                    "", ""
                                )
                            }
                        when (viewModel.dEditor) {
                            titleEditorMarkdown -> ""
                            titleEditorHtml -> showToast(message = toastToAddTagsUl)
                            else -> ""
                        }
                        focusRequester.requestFocus()
                    } else {
                        showToast(toastNotToSelectText)
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.format_list_bulleted),
                        contentDescription = "list_bulleted",
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    if (getSelectedText().isEmpty()) {
                        textFieldValue =
                            when (viewModel.dEditor) {
                                titleEditorMarkdown -> addSymbolAtStartOfLine(
                                    header,
                                    textFieldValue,
                                    "1. ",
                                    "\n"
                                )
                                titleEditorHtml -> addSymbolAtStartOfLine(
                                    header, textFieldValue,
                                    "<li>", ""
                                )
                                else -> addSymbolAtStartOfLine(
                                    header, textFieldValue,
                                    "", ""
                                )
                            }
                        when (viewModel.dEditor) {
                            titleEditorMarkdown -> ""
                            titleEditorHtml -> showToast(message = toastToAddTagsOl)
                            else -> ""
                        }

                        addSymbolAtStartOfLine(header, textFieldValue, "1. ", "\n")
                        focusRequester.requestFocus()
                    } else {
                        showToast(toastNotToSelectText)
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.format_list_numbered),
                        contentDescription = "list_numbered",
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    if (getSelectedText().isNotEmpty()) {
                        textFieldValue = TextFieldValue(
                            text =
                            when (viewModel.dEditor) {
                                titleEditorMarkdown -> addSymbolsToSelectedText("`", "`")
                                titleEditorHtml -> addSymbolsToSelectedText(
                                    "<img src=\"link\">",
                                    "</img>"
                                )
                                else -> ""
                            },
                            selection = textFieldValue.selection
                        )
                    } else {
                        showToast(message = toastToSelectText)
                    }
                }) {
                    Icon(
                        painter = when (viewModel.dEditor) {
                            titleEditorMarkdown -> painterResource(R.drawable.code_tags)
                            titleEditorHtml -> painterResource(R.drawable.file_image)
                            else -> painterResource(R.drawable.file_image)
                        },
                        contentDescription = "code_tags",
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    if (getSelectedText().isNotEmpty()) {
                        textFieldValue = TextFieldValue(
                            text = when (viewModel.dEditor) {
                                titleEditorMarkdown -> addSymbolsToSelectedText("<kbd>", "</kbd>")
                                titleEditorHtml -> addSymbolsToSelectedText(
                                    "<u>",
                                    "</u>"
                                )
                                else -> ""
                            },
                            selection = textFieldValue.selection
                        )
                    } else {
                        showToast(message = toastToSelectText)
                    }
                }) {
                    Icon(
                        painter = when (viewModel.dEditor) {
                            titleEditorMarkdown -> painterResource(R.drawable.keyboard_variant)
                            titleEditorHtml -> painterResource(R.drawable.format_underline)
                            else -> painterResource(R.drawable.file_image)
                        },
                        contentDescription = "keyboard_variant",
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    if (getSelectedText().isEmpty()) {
                        textFieldValue =
                            when (viewModel.dEditor) {
                                titleEditorMarkdown -> addSymbolAtStartOfLine(
                                    header, textFieldValue,
                                    "", "\n---\n"
                                )
                                titleEditorHtml -> addSymbolAtStartOfLine(
                                    header, textFieldValue,
                                    "<br>", "</br>"
                                )
                                else -> addSymbolAtStartOfLine(
                                    header, textFieldValue,
                                    "", ""
                                )
                            }

                        focusRequester.requestFocus()
                    } else {
                        showToast(toastNotToSelectText)
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.minus),
                        contentDescription = "minus",
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    if (getSelectedText().isNotEmpty()) {
                        textFieldValue = TextFieldValue(
                            text = when (viewModel.dEditor) {
                                titleEditorMarkdown -> addSymbolsToSelectedText(
                                    "[Link Text](",
                                    ")"
                                )
                                titleEditorHtml -> addSymbolsToSelectedText(
                                    "<a href=\"http://url\">",
                                    "</a>"
                                )
                                else -> ""
                            },
                            selection = textFieldValue.selection
                        )
                    } else {
                        showToast(message = toastToSelectText)
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.link),
                        contentDescription = "link",
                        tint = Color.White
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(8F)
                    .fillMaxHeight()
                    .verticalScroll(scrollState)
            ) {

                SnackbarHost(
                    snackbarHostState.value,
                )

                when (viewModel.dEditor) {
                    titleEditorMarkdown -> {
                        val parser = Parser.builder().build()
                        val root = parser.parse(textFieldValue.text) as Document
                        Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                            Column {
                                MDDocument(navController as NavHostController, root)
                            }
                        }
                    }
                    titleEditorHtml -> {
                        var showHtmlContentView by remember { mutableStateOf(true) }
                        var content = textFieldValue.text
                        content =
                            content.replace("href=\"/", "href=\"https://wiki.miem.hse.ru/")
                        if (showHtmlContentView) {
                            HtmlContentView(content) {
                                showHtmlContentView = false
                            }
                        }
                    }
                    else -> {}
                }

                SelectionContainer {
                    TextField(
                        value = textFieldValue,
                        onValueChange = { newValue ->
                            textFieldValue = newValue
                        },
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                }
            }

            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(start = 1.dp, end = 1.dp)
                    .weight(1.5F)
            ) {
                ButtonWithImageText(
                    onClick = {
                        showInfoAboutEditorDialog.value = true
                    },
                    colorOfBorder = backgroundBlue,
                    backgroundColor = backgroundBlue,
                    imageId = R.drawable.information_outline,
                    colorOfImage = Color.White,
                    paddingEndOfImg = 0.dp,
                    textOfBtn = "",
                    colorOfText = Color.Black,
                    fontSize = 1.sp,
                    fontWeight = FontWeight.Normal,
                    contentDescription = "infoAboutEditor"
                )

                ButtonWithImageText(
                    onClick = {
                        isCreatePageDialog.value = true
                    },
                    colorOfBorder = greenBtnSave,
                    backgroundColor = greenBtnSave,
                    imageId = R.drawable.check,
                    colorOfImage = Color.White,
                    paddingEndOfImg = 0.dp,
                    textOfBtn = "",
                    colorOfText = Color.Black,
                    fontSize = 1.sp,
                    fontWeight = FontWeight.Normal,
                    contentDescription = "create"
                )
                ButtonWithImageText(
                    onClick = {
                        showSettingsOfPageDialog.value = true
                    },
                    colorOfBorder = Color.Blue,
                    backgroundColor = Color.Blue,
                    imageId = R.drawable.cog,
                    colorOfImage = Color.White,
                    paddingEndOfImg = 0.dp,
                    textOfBtn = "",
                    colorOfText = Color.Black,
                    fontSize = 1.sp,
                    fontWeight = FontWeight.Normal,
                    contentDescription = "settingsOfPage"
                )
                ButtonWithImageText(
                    onClick = {
                        isClearPageDialog.value = true
                    },
                    colorOfBorder = darkRedColor,
                    backgroundColor = darkRedColor,
                    imageId = R.drawable.trash_can_outline,
                    colorOfImage = Color.White,
                    paddingEndOfImg = 0.dp,
                    textOfBtn = "",
                    colorOfText = Color.Black,
                    fontSize = 1.sp,
                    fontWeight = FontWeight.Normal,
                    contentDescription = "clearText"
                )
            }
        }
    }
}







