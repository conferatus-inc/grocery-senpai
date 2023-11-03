package inc.conferatus.grocerysenpai.presentation.mainlist

import ListOfCategories
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import inc.conferatus.grocerysenpai.R
import inc.conferatus.grocerysenpai.presentation.mainlist.component.MainItemTextInputComponent

//import androidx.hilt.navigation.compose.hiltViewModel

// todo TODO!!!!!!!!!!!!
// split onto components
// focus change after typing is finished
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
//@Preview(showSystemUi = true, showBackground = true)
fun MainListScreen(viewModel: MainListViewModel) {
    val groceryListUiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },

        bottomBar = { // FIXME Column ломает отображание предметов в списке сверху (???)
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {

                ListOfCategories(viewModel.itemInput)

                MainItemTextInputComponent(
                    value = viewModel.itemInput,
                    onInsertClick = viewModel::addItem,
                    onValueChange = { value ->
                        viewModel.updateItemInput(value)
                    },
                    isError = !viewModel.isInputValidated
                )
            }
        },

        ) { innerPadding ->
        Column(
            Modifier
                .fillMaxHeight()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            viewModel.categoriesListSingletone.categories.forEach {
                Text(
                    text = it.name
                )
            }

            Text(
                text = "all in all" + viewModel.categoriesListSingletone.categories.size
            )

//            if (groceryListUiState.groceryItems.isEmpty()) {
//                MainNoItemsTextComponent()
//            } else {
//                groceryListUiState.groceryItems.forEach {
//                    MainItemEntryComponent(
//                        mainText = it.category.name,
//                        secondaryText = it.description + " ; bought on " + it.bought,
//                        amountText = "%d %s".format(it.amount, it.amountPostfix),
//                        onRemoveButton = { viewModel.removeItem(it) }
//                    )
//                }
//            }


        }
    }
}
