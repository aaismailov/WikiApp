package com.example.wikiapp.ui.components.rows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wikiapp.R
import com.example.wikiapp.ui.theme.*

@Composable
fun RowForInfo(
    editor: String,
    type: String,
    imageId: Int,
    textBefore: String,
    textAfter: String
) {
    val titleEditorHtml = "html"
    val titleEditorMarkdown = "markdown"

    Row(modifier = Modifier.fillMaxWidth()) {
        Column {
            Row {
                Text(text = type, color = Color.Gray)
                if (imageId != 0)
                    Icon(
                        painter = painterResource(imageId),
                        contentDescription = type,
                        tint = greyBox,
                        modifier = Modifier.padding(start = 5.dp)
                    )
            }

            Row(
                modifier = Modifier
                    .background(greyBox)
                    .padding(5.dp)
            ) {
                Box {
                    Text(text = textBefore, color = Color.White)
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.chevron_down),
                tint = Color.Black,
                contentDescription = "arrow"
            )
            Row(
                Modifier
                    .background(if (type != "Blockquotes") lightBlue else Color.White)
                    .padding(5.dp)) {
                if (type == "Bold" || type == "Italic" || type == "Strikethrough") {
                    Text(
                        text = textAfter, style =
                        when (type) {
                            "Bold" -> TextStyle(fontWeight = FontWeight.Bold)
                            "Italic" -> TextStyle(fontStyle = FontStyle.Italic)
                            "Strikethrough" -> TextStyle(textDecoration = TextDecoration.LineThrough)
                            else -> {
                                TextStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Normal
                                )
                            }
                        }
                    )
                } else {
                    when (type) {
                        "Headers" -> {
                            Text(text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("Header 1\n")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("Header 2\n")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("Header 3\n")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("Header 4\n")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("Header 5\n")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("Header 6")
                                }
                            })
                        }
                        "Unordered Lists" -> {
                            Text(text = textAfter)
                        }
                        "Ordered Lists" -> {
                            Text(text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                       // fontSize = 12.sp
                                    )
                                ) {
                                    append("1. Ordered List Item 1\n")
                                    append("2. Ordered List Item 2\n")
                                    append("3. Ordered List Item 3")
                                }
                            })
                        }
                        "Images" -> {
                            Row(
                                Modifier
                                    .background(lightGray)
                                    .padding(2.dp)) {
                                Text(text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    ) {
                                        append(textAfter)
                                    }
                                })
                            }

                        }
                        "Links" -> {
                            Text(text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        textDecoration = TextDecoration.Underline,
                                        color = Color.Blue
                                    )
                                ) {
                                    append(textAfter)
                                }
                            })
                        }
                        "Underlined Text" -> {
                            Text(text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        textDecoration = TextDecoration.Underline
                                    )
                                ) {
                                    append(textAfter)
                                }
                            })
                        }
                        "Superscript" -> {
                            Text(text = buildAnnotatedString {
                                append("Lorem")
                                withStyle(
                                    style = SpanStyle(
                                        baselineShift = BaselineShift.Superscript,
                                        fontSize = 8.sp
                                    )
                                ) {
                                    append("ipsum")
                                }
                            })
                        }

                        "Subscript" -> {
                            Text(text = buildAnnotatedString {
                                append("Lorem")
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 8.sp,
                                        baselineShift = BaselineShift.Subscript,
                                    )
                                ) {
                                    append("ipsum")
                                }
                            })
                        }

                        "Horizontal Line" -> {
                            Column {
                                Text(text = "Lorem ipsum")
                                Spacer(modifier = Modifier.height(2.dp))
                                when (editor) {
                                    titleEditorMarkdown -> Divider()
                                        titleEditorHtml -> Spacer(modifier = Modifier.height(2.dp))
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(text = "Dolor sit amet")
                            }
                        }

                        "Inline Code" -> {
                            Row(
                                Modifier
                                    .padding(2.dp)) {
                                Text(text = "Lorem ")
                                Box(
                                    Modifier
                                        .padding(2.dp)
                                        .background(lightRedColor)) {
                                    Text(text = " ipsum dolor sit ", color = darkRedColor)
                                }
                                Text(text =" amet")
                            }
                        }

                        "Code Blocks" -> {
                            Row(
                                Modifier
                                    .background(Color.Black)
                                    .padding(4.dp)) {
                               Column {
                                    Text(text = "1\n2\n3", color = Color.Gray)
                               }
                                Column(Modifier.padding(start = 5.dp)) {
                                    Text(text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                color = lightYellowColor
                                            )
                                        ) {
                                            append("function ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                color = Color.White
                                            )
                                        ) {
                                            append("main () {\n" +
                                                    "  echo ")
                                        }

                                        withStyle(
                                            style = SpanStyle(
                                                color = lightGreenColor
                                            )
                                        ) {
                                            append("'Lorem ipsum'\n")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                color = Color.White
                                            )
                                        ) {
                                            append("}")
                                        }
                                    })
                                }
                            }
                        }

                        "Colored Text" -> {
                            Text(text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Red
                                    )
                                ) {
                                    append("Lorem")
                                }
                            })
                        }

                        "Blockquotes" -> {
                            Column {
                                when (editor) {
                                    titleEditorMarkdown -> {
                                        Row(
                                            Modifier
                                                .height(22.dp)
                                                .padding(2.dp),
                                            verticalAlignment = Alignment.CenterVertically) {
                                            Column(
                                                Modifier
                                                    .weight(0.1f)
                                                    .background(middleGray),
                                                horizontalAlignment = Alignment.CenterHorizontally) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.format_quote_open),
                                                    contentDescription = "quote",
                                                    tint = lightGray
                                                )
                                            }
                                            Column(
                                                Modifier
                                                    .weight(0.8f)
                                                    .background(lightGray),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                Text(text = " Quote", style = TextStyle(color = middleGray))
                                            }
                                        }
                                        Row(
                                            Modifier
                                                .height(22.dp)
                                                .padding(2.dp),
                                            verticalAlignment = Alignment.CenterVertically) {
                                            Column(
                                                Modifier
                                                    .weight(0.1f)
                                                    .background(darkBlueColor),
                                                horizontalAlignment = Alignment.CenterHorizontally) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.alpha_i_box_outline),
                                                    contentDescription = "quote info",
                                                    tint = lightBlue
                                                )
                                            }
                                            Column(
                                                Modifier
                                                    .weight(0.8f)
                                                    .background(lightBlue)) {
                                                Text(text = " Quote Info", style = TextStyle(color = darkBlueColor))
                                            }
                                        }
                                        Row(
                                            Modifier
                                                .height(22.dp)
                                                .padding(2.dp),
                                            verticalAlignment = Alignment.CenterVertically) {
                                            Column(
                                                Modifier
                                                    .weight(0.1f)
                                                    .background(darkGreenColor),
                                                horizontalAlignment = Alignment.CenterHorizontally) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.check),
                                                    contentDescription = "quote success",
                                                    tint = lightGreenColor
                                                )
                                            }
                                            Column(
                                                Modifier
                                                    .weight(0.8f)
                                                    .background(lightGreenColor)) {
                                                Text(text = " Quote Success", style = TextStyle(color = darkGreenColor))
                                            }
                                        }
                                        Row(
                                            Modifier
                                                .height(22.dp)
                                                .padding(2.dp),
                                            verticalAlignment = Alignment.CenterVertically) {
                                            Column(
                                                Modifier
                                                    .weight(0.1f)
                                                    .background(darkYellowColor),
                                                horizontalAlignment = Alignment.CenterHorizontally) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.information_outline),
                                                    contentDescription = "quote warning",
                                                    tint = lightYellowColor
                                                )
                                            }
                                            Column(
                                                Modifier
                                                    .weight(0.8f)
                                                    .background(lightYellowColor)) {
                                                Text(text = " Quote Warning", style = TextStyle(color = darkYellowColor))
                                            }
                                        }
                                        Row(
                                            Modifier
                                                .height(22.dp)
                                                .padding(2.dp),
                                            verticalAlignment = Alignment.CenterVertically) {
                                            Column(
                                                Modifier
                                                    .weight(0.1f)
                                                    .background(darkRedColor),
                                                horizontalAlignment = Alignment.CenterHorizontally) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.close_circle_outline),
                                                    contentDescription = "quote danger",
                                                    tint = lightRedColor
                                                )
                                            }
                                            Column(
                                                Modifier
                                                    .weight(0.8f)
                                                    .background(lightRedColor)) {
                                                Text(text = " Quote Danger", style = TextStyle(color = darkRedColor))
                                            }
                                        }
                                    }
                                    titleEditorHtml -> {
                                        Text(text = "\"Quote\"")
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(text = "   Quote Block")
                                        Text(text = "   Quote Block")
                                    }
                                }

                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider()
            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}