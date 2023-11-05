package inc.conferatus.grocerysenpai.presentation.mainlist

import ListOfCategoriesComponent
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import inc.conferatus.grocerysenpai.R
import inc.conferatus.grocerysenpai.presentation.mainlist.component.MainItemEntryComponent
import inc.conferatus.grocerysenpai.presentation.mainlist.component.MainItemTextInputComponent
import inc.conferatus.grocerysenpai.presentation.mainlist.component.MainNoItemsTextComponent

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
            TopAppBar(title = {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall
                )
            })

        },

        bottomBar = { // FIXME Column ломает отображение предметов в списке сверху (???)
            Surface(
                color = Color.Transparent,
            ) {
                Column {
                    Spacer(modifier = Modifier.padding(3.dp))
                    ListOfCategoriesComponent(
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                        viewModel.itemInput,
                        viewModel::addItem
                    )
                    MainItemTextInputComponent(
                        value = viewModel.itemInput,
                        onInsertClick = viewModel::addItem,
                        onValueChange = { value ->
                            viewModel.updateItemInput(value)
                        },
                        isError = !viewModel.isInputValidated,
                        verticalPadding = 2.dp
                    )
                }
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


            if (groceryListUiState.groceryItems.isEmpty()) {
                MainNoItemsTextComponent()
            } else {
                groceryListUiState.groceryItems.forEach {
                    MainItemEntryComponent(
                        mainText = it.category.name,
                        secondaryText = it.description + " ; bought on " + it.bought,
                        amountText = "%d %s".format(it.amount, it.amountPostfix),
                        onRemoveButton = { viewModel.removeItem(it) }
                    )
                }
            }
        }
    }
}
