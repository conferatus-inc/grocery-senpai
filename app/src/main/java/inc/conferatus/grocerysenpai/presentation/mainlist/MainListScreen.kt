package inc.conferatus.grocerysenpai.presentation.mainlist

import ListOfCategoriesComponent
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import inc.conferatus.grocerysenpai.R
import inc.conferatus.grocerysenpai.presentation.mainlist.component.MainItemEntryComponent
import inc.conferatus.grocerysenpai.presentation.mainlist.component.MainItemTextInputComponent
import inc.conferatus.grocerysenpai.presentation.mainlist.component.MainNoItemsTextComponent

//import androidx.hilt.navigation.compose.hiltViewModel

// todo TODO!!!!!!!!!!!!
// focus change after typing is finished
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainListScreen(viewModel: MainListViewModel) {
    val groceryListUiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(50.dp),
                title = {
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth(),
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
            )
        },

        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface
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
            Spacer(modifier = Modifier.padding(5.dp))

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
