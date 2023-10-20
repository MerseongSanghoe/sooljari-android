package com.mssh.sooljari.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mssh.sooljari.R
import com.mssh.sooljari.model.AlcoholRepository
import com.mssh.sooljari.model.AlcoholViewModel
import com.mssh.sooljari.model.AlcoholViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    onNavigateToHome: () -> Unit,
) {
    val viewModel: AlcoholViewModel =
        viewModel(factory = AlcoholViewModelFactory(AlcoholRepository()))
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            SearchAppBar(
                query = query,
                onTextChanged = { query = it },
                onNavigateToHome = onNavigateToHome,
                onSearchButtonClick = {
                    viewModel.getAlcoholList(query)
                    focusManager.clearFocus()
                },
                onKeyboardSearch = {
                    viewModel.getAlcoholList(query)
                    focusManager.clearFocus()
                },
                focusRequester = focusRequester
            )
        }
    ) { paddingValues ->
        SearchResults(
            viewModel = viewModel,
            query = query,
            modifier = Modifier.padding(paddingValues)
        )
    }


}

@Composable
private fun SearchAppBar(
    query: String,
    onTextChanged: (String) -> Unit,
    onNavigateToHome: () -> Unit,
    onSearchButtonClick: () -> Unit,
    onKeyboardSearch: KeyboardActionScope.() -> Unit,
    focusRequester: FocusRequester
) {
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp + statusBarHeight)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_main),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .padding(top = statusBarHeight)
                .matchParentSize()
        ) {
            /*
            TODO: Button 따로 componets에 빼기
             */

            Button(
                modifier = Modifier
                    .size(32.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = colorResource(id = R.color.neutral0)
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = onNavigateToHome,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = null
                )
            }

            SearchTextField(
                text = query,
                onTextChanged = onTextChanged,
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                onKeyboardSearch = onKeyboardSearch
            )

            Button(
                modifier = Modifier
                    .size(32.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = colorResource(id = R.color.neutral0)
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = onSearchButtonClick,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun SearchTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    onKeyboardSearch: KeyboardActionScope.() -> Unit
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChanged,
        modifier = modifier,
        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = onKeyboardSearch
        ),
        singleLine = true,
        cursorBrush = SolidColor(colorResource(id = R.color.black)),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxHeight()
                    .background(
                        color = colorResource(id = R.color.neutral0_alpha65),
                        shape = RoundedCornerShape(3.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.neutral0),
                        shape = RoundedCornerShape(3.dp)
                    )
                    .padding(horizontal = 8.dp)
            ) {
                innerTextField()
            }
        }
    )
}

@Preview(heightDp = 48)
@Composable
private fun SearchAppBarPreview() {
    val home: () -> Unit = {}
    SearchAppBar("", {}, home, home, {}, FocusRequester())
}

/*
@Preview
@Composable
private fun SearchViewPreview() {
    val home: () -> Unit = {}
    SearchView(home)
}*/
