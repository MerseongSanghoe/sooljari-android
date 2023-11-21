package com.mssh.sooljari.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mssh.sooljari.R
import com.mssh.sooljari.model.Alcohol
import com.mssh.sooljari.model.addHash

@Composable
fun VerticalCard(
    alcohol: Alcohol,
    keyword: String = "",
    titleMaxLines: Int = 1,
    onCardClick: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = { alcohol.id?.let { onCardClick(it) } }),
        shape = RoundedCornerShape(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.neutral0)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_placeholder),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(3.dp))
                .background(
                    color = colorResource(id = R.color.neutral5_alpha15)
                ),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )

        // title
        Text(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(),
            text = "${alcohol.name ?: R.string.error_no_value}",
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = titleMaxLines
        )

        // category
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "${alcohol.category ?: R.string.error_no_value}",
            fontSize = 14.sp,
        )

        val tagList = addHash(alcohol.tags)

        TagListLazyRows(
            tagStringList = tagList,
            chip = resultCardChip,
            paddingBetweenChips = 4.dp,
            rowNum = 1,
            keyword = keyword,
            paddingBetweenRows = 4.dp
        )

        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(18.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Gray
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null,
                )
            }

            Text(
                modifier = Modifier
                    .padding(start = 2.dp),
                text = "${1045}명이 별표", /*TODO*/
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun HorizontalCard(
    alcohol: Alcohol,
    keyword: String = "",
    titleMaxLines: Int = 1,
    contentMaxLines: Int = 2,
    onCardClick: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = { alcohol.id?.let { onCardClick(it) } }),
        shape = RoundedCornerShape(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.neutral0)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_placeholder),
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .aspectRatio(1f)
                    .background(
                        color = colorResource(id = R.color.neutral5_alpha15)
                    ),
                contentScale = ContentScale.Fit,
                contentDescription = null
            )

            Row(
                modifier = Modifier
                    .padding(top = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                ) {
                    Text(
                        text = "${alcohol.name ?: R.string.error_no_value}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = titleMaxLines
                    )

                    Text(
                        text = "${alcohol.category ?: R.string.error_no_value}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )

                    val tagList = addHash(alcohol.tags)

                    TagListLazyRows(
                        tagStringList = tagList,
                        chip = resultCardChip,
                        paddingBetweenChips = 4.dp,
                        rowNum = 1,
                        paddingBetweenRows = 4.dp,
                        keyword = keyword
                    )

                    Text(
                        text = "${alcohol.name}${alcohol.name}${alcohol.name}${alcohol.name}${alcohol.name}${alcohol.name}",/*TODO*/
                        fontSize = 10.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = contentMaxLines
                    )
                }
            }
        }
    }
}

@Composable
fun FairCard() {

}

@Composable
fun ResultCard(
    alcohol: Alcohol,
    keyword: String,
    onResultCardClick: (Long) -> Unit
) {
    val cardHeight = 140.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = { alcohol.id?.let { onResultCardClick(it) } }),
        shape = RoundedCornerShape(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.neutral0)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_placeholder),
                modifier = Modifier
                    .size(cardHeight)
                    .background(
                        color = colorResource(id = R.color.neutral5_alpha15)
                    ),
                contentScale = ContentScale.Fit,
                contentDescription = null
            )

            Row(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                ) {
                    Text(
                        text = "${alcohol.name ?: R.string.error_no_value}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${alcohol.category ?: R.string.error_no_value}" +
                                " ${alcohol.degree ?: 0f}도",
                        fontSize = 14.sp,
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )

                    val tagList = addHash(alcohol.tags)

                    TagListLazyRows(
                        tagStringList = tagList,
                        chip = resultCardChip,
                        paddingBetweenChips = 4.dp,
                        rowNum = 2,
                        paddingBetweenRows = 4.dp,
                        keyword = keyword
                    )
                }

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .size(18.dp),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ResultCardPreview() {
    ResultCard(
        Alcohol(
            id = 0L,
            name = "기네스 흑맥주 말고 엄청나게 긴 이름",
            category = "맥주",
            degree = 4.3f,
            tags = testTags
        ),
        keyword = "유하민",
        onResultCardClick = {}
    )
}

@Preview
@Composable
fun VerticalCardPreview() {
    Box(
        modifier = Modifier
            .width(200.dp)
    ) {
        VerticalCard(
            keyword = "플레이브",
            alcohol = Alcohol(
                id = 0L,
                name = "기네스 컴포즈 기네스 컴포즈기네스 컴포즈",
                category = "맛있어보이는 흑맥주",
                degree = 4.3f,
                tags = testTags
            ),
            onCardClick = {})
    }
}

@Preview
@Composable
fun HorizontalCardPreview() {
    Box(
        modifier = Modifier
            .height(110.dp)
    ) {
        HorizontalCard(
            keyword = "플레이브",
            alcohol = Alcohol(
                id = 0L,
                name = "기네스 컴포즈 기네스 컴포즈기네스 컴포즈",
                category = "맛있어보이는 흑맥주",
                degree = 4.3f,
                tags = testTags
            ),
            onCardClick = {})
    }
}
