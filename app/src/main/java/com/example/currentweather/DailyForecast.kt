package com.example.currentweather

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.currentweather.ui.theme.*
import com.example.currentweather.utils.dateCompare
import com.example.currentweather.viewModel.Forecast3HoursStates
import com.example.currentweather.viewModel.MainViewModel
import java.util.*
import androidx.compose.foundation.lazy.LazyRow as LazyRow1

@Composable
fun DailyForecast(viewModel: MainViewModel, navController: NavHostController) {

    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp
    val dailyForecast: MutableSet<String> = mutableSetOf()

    when (val forcastState = viewModel.forecast3Hours.collectAsState().value) {

        is Forecast3HoursStates.Loading -> {}

        is Forecast3HoursStates.Loaded -> {

            forcastState.data.list!!.forEach {
                dailyForecast.add(dateCompare(it!!.dt_txt!!, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd")!!)
            }
            var currentDate by remember { mutableStateOf(dailyForecast.first()) }

            var oneDayForcast = forcastState.data.list.filter {
                dateCompare(
                    it!!.dt_txt!!,
                    "yyyy-MM-dd HH:mm:ss",
                    "yyyy-MM-dd"
                ) == currentDate
            }

            Column(
            ) {
                Column(
                    Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    skyblue,
                                    skybluedark
                                )
                            )
                        )
                        .padding(top = 10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(horizontal = 20.dp)
                    ) {

                        Card(
                            shape = RoundedCornerShape(12.dp), modifier = Modifier
                                .height(40.dp)
                                .width(40.dp)
                                .clickable(indication = null, interactionSource = remember {
                                    MutableInteractionSource()
                                }) {
                                    navController.popBackStack()
                                }, backgroundColor = Color.White
                        ) {
                            Icon(
                                Icons.Default.ArrowBackIosNew,
                                contentDescription = "",
                                tint = Color.Black,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Text(
                            text = "Next 5 Days",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(40.dp))
                    }
                    LazyRow1(
                        Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .padding(vertical = 15.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        item {
                            Box(modifier = Modifier.width(1.dp)) {
                            }
                        }
                        items(dailyForecast.size) {
                            Card(
                                shape = RoundedCornerShape(50.dp),
                                elevation = 0.dp,
                                modifier = Modifier
                                    .fillMaxHeight()
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .background(
                                            brush = if (dailyForecast.elementAt(it) == currentDate) {
                                                Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color.White,
                                                        Color.White
                                                    )
                                                )
                                            } else
                                                Brush.verticalGradient(
                                                    colors = listOf(
                                                        skyblue,
                                                        skybluedark
                                                    )
                                                )
                                        )
                                        .width(70.dp)
                                        .padding(10.dp)
                                        .clickable(indication = null, interactionSource = remember {
                                            MutableInteractionSource()
                                        }) {
                                            currentDate = (dailyForecast.elementAt(it))
                                        }
                                ) {
                                    Image(
                                        painter = painterResource(
                                            id = when (forcastState.data.list?.get(it)!!.weather!![0]!!.icon) {
                                                "01d" -> {
                                                    R.drawable.i01d
                                                }
                                                "01n" -> {
                                                    R.drawable.i01n
                                                }
                                                "02d" -> {
                                                    R.drawable.i02d
                                                }
                                                "02n" -> {
                                                    R.drawable.i02n
                                                }
                                                "03d" -> {
                                                    R.drawable.i03d
                                                }
                                                "03n" -> {
                                                    R.drawable.i03n
                                                }
                                                "04d" -> {
                                                    R.drawable.i04d
                                                }
                                                "04n" -> {
                                                    R.drawable.i04n
                                                }
                                                "09d" -> {
                                                    R.drawable.i09d
                                                }
                                                "09n" -> {
                                                    R.drawable.i09d
                                                }
                                                "10d" -> {
                                                    R.drawable.i09d
                                                }
                                                "10n" -> {
                                                    R.drawable.i09d
                                                }
                                                "11d" -> {
                                                    R.drawable.i11d
                                                }
                                                "11n" -> {
                                                    R.drawable.i11d
                                                }
                                                "13d" -> {
                                                    R.drawable.i13n
                                                }
                                                "13n" -> {
                                                    R.drawable.i13n
                                                }
                                                "50d" -> {
                                                    R.drawable.i50n
                                                }
                                                "50n" -> {
                                                    R.drawable.i50n
                                                }
                                                else -> {
                                                    R.drawable.i01d
                                                }
                                            }
                                        ),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .height((height * 0.05).dp)
                                            .width((width * 0.08).dp),
                                        alignment = Alignment.TopCenter
                                    )
                                    Column() {
                                        Text(
                                            text = dateCompare(
                                                dailyForecast.elementAt(it),
                                                "yyyy-MM-dd",
                                                "dd"
                                            )!!,
                                            color = if (dailyForecast.elementAt(it) == currentDate) {
                                                Purple200
                                            } else Color.White,
                                            fontSize = 25.sp,
                                            fontWeight = FontWeight.W800,
                                            textAlign = TextAlign.Center
                                        )
                                        Text(
                                            text = dateCompare(
                                                dailyForecast.elementAt(it),
                                                "yyyy-MM-dd",
                                                "EEE"
                                            )!!,
                                            color = if (dailyForecast.elementAt(it) == currentDate) {
                                                Purple200
                                            } else Color.White,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                        item {
                            Box(modifier = Modifier.width(1.dp)) {
                            }
                        }
                    }
                }

                Card(
                    shape = RoundedCornerShape(topEnd = 25.dp, topStart = 25.dp),
                    modifier = Modifier.background(skybluedark)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Color.Transparent
                                    )
                                    .padding(top = 20.dp)
                            ) {
                                Card(
                                    elevation = 20.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 35.dp)
                                        .height((height * 0.4).dp)
                                        .padding(start = 20.dp, end = 20.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                brush = Brush.verticalGradient(
                                                    colors = listOf(
                                                        skyblue,
                                                        skybluedark
                                                    )
                                                )
                                            )
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    Color.Transparent
                                                )
                                        ) {
                                            Card(
                                                elevation = 0.dp,
                                                shape = RoundedCornerShape(35.dp),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height((height * 0.20).dp),
                                                backgroundColor = Color.Transparent
                                            ) {
                                                Row(
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    modifier = Modifier
                                                        .padding(
                                                            top = 10.dp,
                                                            bottom = 15.dp,
                                                            start = 20.dp,
                                                            end = 20.dp
                                                        )
                                                        .background(
                                                            Color.Transparent
                                                        )
                                                        .fillMaxSize(),
                                                ) {
                                                    Text(
                                                        oneDayForcast.first()?.weather?.first()!!.description!!.uppercase(
                                                            Locale.getDefault()
                                                        ),
                                                        color = Color.White,
                                                        fontSize = 20.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.align(Alignment.Bottom)
                                                    )
                                                    Column() {
                                                        Text(
                                                            text = buildAnnotatedString {
                                                                append(
                                                                    "${((oneDayForcast.first()?.main?.temp!! - 273.15)).toInt()}"
                                                                )
                                                                withStyle(
                                                                    SpanStyle(
                                                                        baselineShift = BaselineShift.Superscript,
                                                                        color = Color.White,
                                                                        fontSize = 30.sp
                                                                    )
                                                                ) {
                                                                    append("o")
                                                                }
                                                            },
                                                            color = Color.White,
                                                            fontSize = 60.sp,
                                                            fontWeight = FontWeight.W800,
                                                            textAlign = TextAlign.End
                                                        )
                                                        Text(
                                                            text = buildAnnotatedString {
                                                                append(
                                                                    "Feels LIke ${((oneDayForcast.first()?.main?.feels_like!! - 273.15)).toInt()}"
                                                                )
                                                                withStyle(
                                                                    SpanStyle(
                                                                        baselineShift = BaselineShift.Superscript,
                                                                        color = Color.White,
                                                                        fontSize = 10.sp
                                                                    )
                                                                ) {
                                                                    append("o")
                                                                }
                                                            }, color = Color.White,
                                                            fontSize = 15.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            textAlign = TextAlign.End
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Column() {

                                                Card(
                                                    elevation = 0.dp,
                                                    shape = RoundedCornerShape(25.dp),
                                                    modifier = Modifier
                                                        .padding(top = 35.dp),
                                                    backgroundColor = Color.White.copy(alpha = 0.1f)
                                                ) {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.i04d),
                                                        contentDescription = "",
                                                        modifier = Modifier
                                                            .height((width * 0.18).dp)
                                                            .width((width * 0.18).dp)
                                                            .padding(15.dp),
                                                        alignment = Alignment.TopCenter
                                                    )
                                                }
                                                Text(
                                                    modifier = Modifier
                                                        .width((width * 0.18).dp)
                                                        .padding(vertical = 10.dp),
                                                    text = oneDayForcast[0]?.clouds?.all.toString() + "%",
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White
                                                )
                                            }
                                            Column() {

                                                Card(
                                                    elevation = 0.dp,
                                                    shape = RoundedCornerShape(25.dp),
                                                    modifier = Modifier
                                                        .padding(top = 35.dp),
                                                    backgroundColor = Color.White.copy(alpha = 0.1f)
                                                ) {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.wind),
                                                        contentDescription = "",
                                                        modifier = Modifier
                                                            .height((width * 0.18).dp)
                                                            .width((width * 0.18).dp)
                                                            .padding(15.dp),
                                                        alignment = Alignment.TopCenter,
                                                        colorFilter = ColorFilter.tint(Color.Black),
                                                    )
                                                }
                                                Text(
                                                    modifier = Modifier
                                                        .width((width * 0.18).dp)
                                                        .padding(vertical = 10.dp),
                                                    text = (oneDayForcast[0]?.wind?.speed!! * 3600 / 1000).toInt()
                                                        .toString() + " km/h",
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White
                                                )
                                            }
                                            Column() {

                                                Card(
                                                    elevation = 0.dp,
                                                    shape = RoundedCornerShape(25.dp),
                                                    modifier = Modifier
                                                        .padding(top = 35.dp),
                                                    backgroundColor = Color.White.copy(alpha = 0.1f)
                                                ) {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.humidity),
                                                        contentDescription = "",
                                                        modifier = Modifier
                                                            .height((width * 0.18).dp)
                                                            .width((width * 0.18).dp)
                                                            .padding(10.dp),
                                                        alignment = Alignment.TopCenter
                                                    )
                                                }

                                                Text(
                                                    modifier = Modifier
                                                        .width((width * 0.18).dp)
                                                        .padding(vertical = 10.dp),
                                                    text = oneDayForcast[0]?.main?.humidity.toString() + "%",
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White
                                                )
                                            }
                                        }
                                    }
                                }
                                Image(
                                    painter = painterResource(
                                        id = when (oneDayForcast[0]?.weather?.first()?.icon) {
                                            "01d" -> {
                                                R.drawable.i01d
                                            }
                                            "01n" -> {
                                                R.drawable.i01n
                                            }
                                            "02d" -> {
                                                R.drawable.i02d
                                            }
                                            "02n" -> {
                                                R.drawable.i02n
                                            }
                                            "03d" -> {
                                                R.drawable.i03d
                                            }
                                            "03n" -> {
                                                R.drawable.i03n
                                            }
                                            "04d" -> {
                                                R.drawable.i04d
                                            }
                                            "04n" -> {
                                                R.drawable.i04n
                                            }
                                            "09d" -> {
                                                R.drawable.i09d
                                            }
                                            "09n" -> {
                                                R.drawable.i09d
                                            }
                                            "10d" -> {
                                                R.drawable.i09d
                                            }
                                            "10n" -> {
                                                R.drawable.i09d
                                            }
                                            "11d" -> {
                                                R.drawable.i11d
                                            }
                                            "11n" -> {
                                                R.drawable.i11d
                                            }
                                            "13d" -> {
                                                R.drawable.i13n
                                            }
                                            "13n" -> {
                                                R.drawable.i13n
                                            }
                                            "50d" -> {
                                                R.drawable.i50n
                                            }
                                            "50n" -> {
                                                R.drawable.i50n
                                            }
                                            else -> {
                                                R.drawable.i01d
                                            }
                                        }
                                    ),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .height((height * 0.17).dp)
                                        .width((width * 0.5).dp),
                                    alignment = Alignment.TopCenter
                                )
                            }
                        }
                        item { Spacer(modifier = Modifier.height(15.dp)) }
                        items(oneDayForcast) {

                            Card(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .height(90.dp)
                                    .padding(horizontal = 20.dp, vertical = 7.dp),
                                backgroundColor = Color.White,
                                shape = RoundedCornerShape(15.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 15.dp)
                                ) {
                                    Text(
                                        text = dateCompare(
                                            it!!.dt_txt.toString(),
                                            "yyyy-MM-dd HH:mm:ss",
                                            "HH:mm"
                                        ).toString(),
                                        textAlign = TextAlign.Center,
                                        color = skybluedark,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                SpanStyle(
                                                    fontSize = 20.sp
                                                )
                                            ) {
                                                append("${((it.main!!.temp!! - 273.15)).toInt()}")
                                            }
                                            withStyle(
                                                SpanStyle(
                                                    baselineShift = BaselineShift.Superscript,
                                                    fontSize = 15.sp
                                                )
                                            ) {
                                                append("o")
                                            }
                                            append(
                                                "/${((it.main!!.feels_like!! - 273.15)).toInt()}"
                                            )
                                            withStyle(
                                                SpanStyle(
                                                    baselineShift = BaselineShift.Superscript,
                                                    fontSize = 10.sp
                                                )
                                            ) {
                                                append("o")
                                            }
                                        },
                                        textAlign = TextAlign.Center,
                                        color = Color.LightGray,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Column(verticalArrangement = Arrangement.SpaceBetween) {
                                        Image(
                                            painter = painterResource(
                                                id = when (it.weather?.first()?.icon) {
                                                    "01d" -> {
                                                        R.drawable.i01d
                                                    }
                                                    "01n" -> {
                                                        R.drawable.i01n
                                                    }
                                                    "02d" -> {
                                                        R.drawable.i02d
                                                    }
                                                    "02n" -> {
                                                        R.drawable.i02n
                                                    }
                                                    "03d" -> {
                                                        R.drawable.i03d
                                                    }
                                                    "03n" -> {
                                                        R.drawable.i03n
                                                    }
                                                    "04d" -> {
                                                        R.drawable.i04d
                                                    }
                                                    "04n" -> {
                                                        R.drawable.i04n
                                                    }
                                                    "09d" -> {
                                                        R.drawable.i09d
                                                    }
                                                    "09n" -> {
                                                        R.drawable.i09d
                                                    }
                                                    "10d" -> {
                                                        R.drawable.i09d
                                                    }
                                                    "10n" -> {
                                                        R.drawable.i09d
                                                    }
                                                    "11d" -> {
                                                        R.drawable.i11d
                                                    }
                                                    "11n" -> {
                                                        R.drawable.i11d
                                                    }
                                                    "13d" -> {
                                                        R.drawable.i13n
                                                    }
                                                    "13n" -> {
                                                        R.drawable.i13n
                                                    }
                                                    "50d" -> {
                                                        R.drawable.i50n
                                                    }
                                                    "50n" -> {
                                                        R.drawable.i50n
                                                    }
                                                    else -> {
                                                        R.drawable.i01d
                                                    }
                                                }
                                            ),
                                            contentDescription = "",
                                            modifier = Modifier.height(35.dp),
                                        )
                                        Text(
                                            text = it.weather?.first()?.main.toString(),
                                            color = Purple200,
                                            fontWeight = FontWeight.Bold
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
