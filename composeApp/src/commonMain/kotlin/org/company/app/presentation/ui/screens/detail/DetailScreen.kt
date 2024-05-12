package org.company.app.presentation.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.company.app.domain.model.crypto.Data
import org.company.app.presentation.ui.components.CurrencyImage
import org.company.app.theme.LocalThemeIsDark
import kotlin.math.roundToInt

class DetailScreen(
    private val data: Data,
) : Screen {
    @Composable
    override fun Content() {
        DetailContent(data)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(data: Data) {
    var selectedPeriod by remember { mutableStateOf("1H") }
    val capList = remember { mutableListOf("1H", "1D", "1W", "1M", "3M", "6M", "1Y") }
    val isDark by LocalThemeIsDark.current
    val navigator = LocalNavigator.current
    val textColor = if (isDark) Color.White else Color.Black
    val percentChange24h = data.quote.uSD.percentChange24h
    val capMarket = data.quote.uSD.percentChange30d
    val textColor24h = if (percentChange24h > 0) Color.Green else Color.Red
    val textColor1h = if (capMarket > 0) Color.Green else Color.Red
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "",
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIosNew,
                        contentDescription = "Menu Icon",
                        modifier = Modifier.clickable {
                            navigator?.pop()
                        }
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications Icon"
                    )
                    Icon(
                        imageVector = Icons.Outlined.StarOutline,
                        contentDescription = "Favourite Icon"
                    )
                },
                modifier = Modifier.fillMaxWidth()
                    .height(49.dp)
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(top = it.calculateTopPadding())
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CurrencyImage(
                    id = data.id,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = data.name,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val priceString = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = Color.Gray,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("$ ")
                    }
                    withStyle(
                        SpanStyle(
                            color = textColor,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("$" + "${((data.quote.uSD.price * 100).roundToInt()) / 100.0}")
                    }
                    withStyle(
                        SpanStyle(
                            color = Color.Gray,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(" USD")
                    }

                }
                Text(
                    text = priceString
                )
                Text(
                    text = "${percentChange24h.roundToInt()}%",
                    color = textColor24h,
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CurrencyImage(
                    id = data.id,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                val latestCap = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("${data.quote.uSD.fullyDilutedMarketCap}")
                    }
                    withStyle(
                        SpanStyle(
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(" ${data.symbol}")
                    }

                }
                Text(
                    text = latestCap
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${capMarket.roundToInt()}%",
                    color = textColor1h,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TabRow(
                selectedTabIndex = capList.indexOf(selectedPeriod),
                containerColor = Color.White,
                contentColor = Color.Black,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(
                            tabPositions[capList.indexOf(
                                selectedPeriod
                            )]
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                capList.forEachIndexed { index, period ->
                    Tab(
                        selected = selectedPeriod == period,
                        onClick = { selectedPeriod = period },
                        text = { Text(text = period) },
                    )
                }
            }
            when(selectedPeriod) {
                "1H" -> {
                    Text("1H Selected")
                }
                "1D" -> {
                    Text("1D Selected")
                }
                "1W" -> {
                    Text("1W Selected")
                }
                "1M" -> {
                    Text("1M Selected")
                }
                "3M" -> {
                    Text("3M Selected")
                }
                "6M" -> {
                    Text("6M Selected")
                }
                "1Y" -> {
                    Text("1Y Selected")
                }

            }
        }

    }
}