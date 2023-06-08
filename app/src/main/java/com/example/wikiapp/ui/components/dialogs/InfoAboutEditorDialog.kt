package com.example.wikiapp.ui.components.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.wikiapp.R
import com.example.wikiapp.ui.components.rows.HeaderForDialogRow
import com.example.wikiapp.ui.components.rows.RowForInfo
import com.example.wikiapp.ui.theme.darkBlueColor

@Composable
fun InfoAboutEditorDialog(
    editor: String,
    setShowDialog: (Boolean) -> Unit
) {
    val titleEditorHtml = "html"
    val titleEditorMarkdown = "markdown"
    val titleMarkup = "Разметка"

    val scrollStateVer = rememberScrollState()
    val mapOfTypesForMarkdown = mapOf(
        0 to "Bold",
        1 to "Italic",
        2 to "Strikethrough",
        3 to "Headers",
        4 to "Unordered Lists",
        5 to "Ordered Lists",
        6 to "Images",
        7 to "Links",
        8 to "Superscript",
        9 to "Subscript",
        10 to "Horizontal Line",
        11 to "Inline Code",
        12 to "Code Blocks",
        13 to "Blockquotes"
    )

    val mapOfTextBeforeAfterForMarkdown = mapOf(
        Pair(
            mapOfTypesForMarkdown[0],
            listOf("**Lorem ipsum**", "Lorem ipsum", R.drawable.format_bold)
        ),
        Pair(
            mapOfTypesForMarkdown[1],
            listOf("*Lorem ipsum*", "Lorem ipsum", R.drawable.format_italic)
        ),
        Pair(
            mapOfTypesForMarkdown[2],
            listOf("~~Lorem ipsum~~", "Lorem ipsum", R.drawable.format_strikethrough)
        ),
        Pair(
            mapOfTypesForMarkdown[3], listOf(
                "# Header 1\n" +
                        "## Header 2\n" +
                        "### Header 3\n" +
                        "#### Header 4\n" +
                        "##### Header 5\n" +
                        "###### Header 6", "Header 1\n" +
                        "Header 2\n" +
                        "Header 3\n" +
                        "Header 4\n" +
                        "Header 5\n" +
                        "Header 6", R.drawable.format_header_pound
            )
        ),

        Pair(
            mapOfTypesForMarkdown[4], listOf(
                "- Unordered List Item 1\n" +
                        "- Unordered List Item 2\n" +
                        "- Unordered List Item 3", "- Unordered List Item 1\n" +
                        "- Unordered List Item 2\n" +
                        "- Unordered List Item 3", R.drawable.format_list_bulleted
            )
        ),

        Pair(
            mapOfTypesForMarkdown[5], listOf(
                "1. Ordered List Item 1\n" +
                        "1. Ordered List Item 2\n" +
                        "1. Ordered List Item 3", "1. Ordered List Item 1\n" +
                        "2. Ordered List Item 2\n" +
                        "3. Ordered List Item 3", R.drawable.format_list_numbered
            )
        ),

        Pair(
            mapOfTypesForMarkdown[6],
            listOf("![Caption Text](/path/to/image.jpg)", "150 x 50", 0)
        ),
        Pair(
            mapOfTypesForMarkdown[7], listOf(
                "[Link Text](https://wiki.js.org)", "Link Text",
                R.drawable.link
            )
        ),
        Pair(
            mapOfTypesForMarkdown[8],
            listOf("Lorem ^ipsum^", "Lorem ipsum", R.drawable.format_superscript)
        ),
        Pair(
            mapOfTypesForMarkdown[9],
            listOf("Lorem ~ipsum~", "Lorem ipsum", R.drawable.format_subscript)
        ),
        Pair(
            mapOfTypesForMarkdown[10], listOf(
                "Lorem ipsum\n" +
                        "---\n" +
                        "Dolor sit amet", "Lorem ipsum\n" +
                        "Dolor sit amet", R.drawable.minus
            )
        ),
        Pair(
            mapOfTypesForMarkdown[11], listOf(
                "Lorem `ipsum dolor sit` amet", "Lorem ipsum dolor sit amet",
                R.drawable.code_tags
            )
        ),
        Pair(
            mapOfTypesForMarkdown[12], listOf(
                "```js\n" +
                        "function main () {\n" +
                        "echo 'Lorem ipsum'\n" +
                        "}\n" +
                        "```", "function main () {\n" +
                        "  echo 'Lorem ipsum'\n" +
                        "}", 0
            )
        ),
        Pair(
            mapOfTypesForMarkdown[13], listOf(
                "> Quote\n" +
                        "> Quote Info\n{.is-info}\n" +
                        "> Quote Success\n{.is-success}\n" +
                        "> Quote Warning\n{.is-warning}\n" +
                        "> Quote Danger\n{.is-danger}", "", R.drawable.alpha_t_box_outline
            )
        ),
    )

    val mapOfTagsForHtml = mapOf(
        0 to "Bold",
        1 to "Italic",
        2 to "Strikethrough",
        3 to "Headers",
        4 to "Unordered Lists",
        5 to "Ordered Lists",
        6 to "Underlined Text",
        7 to "Links",
        8 to "Superscript",
        9 to "Subscript",
        10 to "Horizontal Line",
        11 to "Images",
        12 to "Colored Text",
        13 to "Blockquotes"
    )

    val mapOfTextBeforeAfterForHtml = mapOf(
        Pair(
            mapOfTagsForHtml[0],
            listOf("<b>Lorem ipsum</b>", "Lorem ipsum", R.drawable.format_bold)
        ),
        Pair(
            mapOfTagsForHtml[1],
            listOf("<i>Lorem ipsum</i>", "Lorem ipsum", R.drawable.format_italic)
        ),
        Pair(
            mapOfTagsForHtml[2],
            listOf("<s>Lorem ipsum</s>", "Lorem ipsum", R.drawable.format_strikethrough)
        ),
        Pair(
            mapOfTagsForHtml[3], listOf(
                "<h1>Header 1</h1>\n" +
                        "<h2>Header 2</h2>\n" +
                        "<h3>Header 3</h3>\n" +
                        "<h4>Header 4</h4>\n" +
                        "<h5>Header 5</h5>\n" +
                        "<h6>Header 6</h6>", "Header 1\n" +
                        "Header 2\n" +
                        "Header 3\n" +
                        "Header 4\n" +
                        "Header 5\n" +
                        "Header 6", R.drawable.format_header_pound
            )
        ),

        Pair(
            mapOfTagsForHtml[4], listOf(
                "<ul>\n" +
                        "<li> Unordered List Item 1\n" +
                        "<li> Unordered List Item 2\n" +
                        "<li> Unordered List Item 3\n" + "</ul>",
                "* Unordered List Item 1\n" +
                        "* Unordered List Item 2\n" +
                        "* Unordered List Item 3", R.drawable.format_list_bulleted
            )
        ),

        Pair(
            mapOfTagsForHtml[5], listOf(
                "1. Ordered List Item 1\n" +
                        "1. Ordered List Item 2\n" +
                        "1. Ordered List Item 3", "1. Ordered List Item 1\n" +
                        "2. Ordered List Item 2\n" +
                        "3. Ordered List Item 3", R.drawable.format_list_numbered
            )
        ),

        Pair(
            mapOfTagsForHtml[6],
            listOf("<u>Lorem</u>", "Lorem", R.drawable.format_underline)
        ),
        Pair(mapOfTagsForHtml[7], listOf("<a href=\"http://url\">URL</a>", "URL", R.drawable.link)),
        Pair(
            mapOfTagsForHtml[8],
            listOf("Lorem <sup>ipsum</sup>", "Lorem ipsum", R.drawable.format_superscript)
        ),
        Pair(
            mapOfTagsForHtml[9],
            listOf("Lorem <sub>ipsum</sub>", "Lorem ipsum", R.drawable.format_subscript)
        ),
        Pair(
            mapOfTagsForHtml[10], listOf(
                "Lorem ipsum <br></br>" +
                        "Dolor sit amet", "Lorem ipsum\n\n" +
                        "Dolor sit amet", R.drawable.minus
            )
        ),
        Pair(
            mapOfTagsForHtml[11], listOf(
                "<img src=\"link\"></img>", "150 x 50",
                R.drawable.file_image
            )
        ),
        Pair(
            mapOfTagsForHtml[12], listOf(
                "<font color=\"red\">Lorem</font>", "Lorem", 0
            )
        ),
        Pair(
            mapOfTagsForHtml[13], listOf(
                "<q>Quote</q>\n" +
                        "<blockquote>Quote Block\nQuote Block</blockquote>",
                "",
                R.drawable.alpha_t_box_outline
            )
        ),
    )

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .width(250.dp)
                .height(500.dp)
                .padding(start = 5.dp, end = 5.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeaderForDialogRow(
                        imageId = R.drawable.information_outline,
                        colorImage = darkBlueColor,
                        title = "$titleMarkup $editor"
                    ) { setShowDialog(false) }
                    Column(modifier = Modifier.verticalScroll(scrollStateVer)) {
                        when (editor) {
                            titleEditorMarkdown -> {
                                mapOfTextBeforeAfterForMarkdown.forEach { (key, value) ->
                                    if (key != null) {
                                        RowForInfo(
                                            editor = titleEditorMarkdown,
                                            type = key,
                                            imageId = value[2] as Int,
                                            textBefore = value[0] as String,
                                            textAfter = value[1] as String
                                        )
                                    }
                                }
                            }
                            titleEditorHtml -> {
                                mapOfTextBeforeAfterForHtml.forEach { (key, value) ->
                                    if (key != null) {
                                        RowForInfo(
                                            editor = titleEditorHtml,
                                            type = key,
                                            imageId = value[2] as Int,
                                            textBefore = value[0] as String,
                                            textAfter = value[1] as String
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}