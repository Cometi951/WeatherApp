package com.example.currentweather

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.currentweather.model.ForeCast3HoursModel
import com.example.currentweather.screen.Screen
import com.example.currentweather.ui.theme.*
import com.example.currentweather.utils.convertDate
import com.example.currentweather.utils.dateCompare
import com.example.currentweather.utils.getmDate
import com.example.currentweather.viewModel.CurrentWeatherStates
import com.example.currentweather.viewModel.Forecast3HoursStates
import com.example.currentweather.viewModel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Home(viewModel: MainViewModel, navController: NavHostController) {

    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp
    var todayDate: String = ""
    var todayTime = SimpleDateFormat("HH.mm", Locale.getDefault()).format(Date()).toFloat()
    var todayDateList: ArrayList<ForeCast3HoursModel.Data> = ArrayList()

    Scaffold(
        backgroundColor = backgroundAllTheme
    ) {

        when (val state = viewModel.currentWeather.collectAsState().value) {
            is CurrentWeatherStates.Loading -> {

                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is CurrentWeatherStates.Error -> {

                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.message)
                }
            }
            is CurrentWeatherStates.Loaded -> {

                Column(
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(top = 15.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((height * 0.25).dp)
                            .padding(top = 10.dp, end = 25.dp, start = 25.dp),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {

                        Column(Modifier.weight(6f)) {
                            Text(
                                text = state.data.name!!,
                                fontSize = 27.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                modifier = Modifier,
                                text = getmDate(state.data.dt!!.toLong())!!,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black.copy(alpha = 0.2f),
                                fontSize = 13.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .background(Color.Transparent)
                            ) {
                            BottomShadow(alpha = 0.60f,width = 98.dp,height= 30.dp,yOffset = 72.dp)
                            Image(
                                painter = painterResource(id = R.drawable.maps),
                                contentDescription = "Localized description",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(100.dp, 100.dp)
                                    .clip(RoundedCornerShape(20.dp))
                            )

                        }

//                        Surface(
//                            color = Color.Transparent,
//                            elevation = 80.dp,
//                            shape = RoundedCornerShape(20.dp),
//                            modifier = Modifier.width(120.dp).height(120.dp)
//
//                        ) {
//                            Column {
//                                Image(
//                                    painter = painterResource(id = R.drawable.maps),
//                                    contentDescription = "",
//                                    contentScale = ContentScale.Crop,
//                                )
//                                BottomShadow(alpha = 0.1f, height = 50.dp)
//                            }
//                        }

//                        Card(
//                            elevation = 0.dp,
//                            shape = RoundedCornerShape(30.dp),
//                            modifier = Modifier
//                                .wrapContentSize()
//                                .weight(4f)
//                                .padding(10.dp),
//                            backgroundColor = Color.Transparent
//                        ) {
//                            Image(
//                                modifier = Modifier,
//                                painter = painterResource(id = R.drawable.maps),
//                                contentDescription = "",
//                            )
//                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Card(
                                elevation = 20.dp,
                                shape = RoundedCornerShape(30.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 35.dp)
                                    .height((height * 0.27).dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .background(
                                            brush = Brush.verticalGradient(
                                                colors = listOf(
                                                    skyblue,
                                                    skybluedark
                                                )
                                            )
                                        )
                                        .padding(
                                            top = 0.dp,
                                            bottom = 30.dp,
                                            start = 20.dp,
                                            end = 20.dp
                                        )
                                        .fillMaxSize(),
                                ) {
                                    Text(
                                        state.data.weather!![0]!!.description!!.substring(0, 1)
                                            .toUpperCase() + state.data.weather!![0]!!.description!!.substring(
                                            1
                                        ).toLowerCase(),
                                        color = Color.White,
                                        fontSize = 27.sp,
                                        fontWeight = FontWeight.W800,
                                        fontFamily = FontFamily.Serif,
                                        modifier = Modifier
                                            .align(Alignment.Bottom)
                                            .weight(6f)
                                    )
                                    Column(Modifier.weight(5f), horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "${((state.data.main!!.temp!! - 273.15)).toInt()}°",
                                            modifier = Modifier
                                                .graphicsLayer(alpha = 0.99f)
                                                .drawWithCache {
                                                    val brush = Brush.verticalGradient(
                                                        listOf(
                                                            Color.White,
                                                            skyblue
                                                        )
                                                    )
                                                    onDrawWithContent {
                                                        drawContent()
                                                        drawRect(
                                                            brush,
                                                            blendMode = BlendMode.SrcAtop
                                                        )
                                                    }
                                                },
                                            fontSize = 70.sp,
                                            fontWeight = FontWeight.W700,
                                            lineHeight = 60.sp,
                                        )
                                        Text(
                                            modifier = Modifier,
                                            text = "Feels like ${((state.data.main!!.feels_like!! - 273.15)).toInt()}°",
                                            color = Color.White,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.End,
                                        )
                                    }
                                }
                            }
                            Image(
                                painter = painterResource(
                                    id = when (state.data.weather!![0]!!.icon) {
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

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column() {

                                Card(
                                    elevation = 0.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier
                                        .padding(top = 35.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.i04d),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .height((width * 0.18).dp)
                                            .width((width * 0.18).dp)
                                            .background(
                                                Color.Blue.copy(alpha = 0.05f)
                                            )
                                            .padding(15.dp),
                                        alignment = Alignment.TopCenter
                                    )
                                }

                                Text(
                                    modifier = Modifier
                                        .width((width * 0.18).dp)
                                        .padding(vertical = 10.dp),
                                    text = state.data.clouds?.all.toString() + "%",
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Column() {

                                Card(
                                    elevation = 0.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier
                                        .padding(top = 35.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.wind),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .height((width * 0.18).dp)
                                            .width((width * 0.18).dp)
                                            .background(
                                                Color.Blue.copy(alpha = 0.05f)
                                            )
                                            .padding(15.dp),
                                        alignment = Alignment.TopCenter,
                                        colorFilter = ColorFilter.tint(Color.Black)
                                    )
                                }

                                Text(
                                    modifier = Modifier
                                        .width((width * 0.18).dp)
                                        .padding(vertical = 10.dp),
                                    text = (state.data.wind?.speed!! * 3600 / 1000).toInt()
                                        .toString() + " km/h",
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Column() {

                                Card(
                                    elevation = 0.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier
                                        .padding(top = 35.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.humidity),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .height((width * 0.18).dp)
                                            .width((width * 0.18).dp)
                                            .background(
                                                Color.Blue.copy(alpha = 0.05f)
                                            )
                                            .padding(10.dp),
                                        alignment = Alignment.TopCenter
                                    )
                                }

                                Text(
                                    modifier = Modifier
                                        .width((width * 0.18).dp)
                                        .padding(vertical = 10.dp),
                                    text = state.data.main?.humidity.toString() + "%",
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                    }

                    when (val forcastState =
                        viewModel.forecast3Hours.collectAsState().value) {
                        is Forecast3HoursStates.Loading -> {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }

                        is Forecast3HoursStates.Error -> {

                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(text = forcastState.message)
                            }
                        }

                        is Forecast3HoursStates.Loaded -> {

                            if (todayDate.isBlank()) {
                                todayDate = dateCompare(
                                    forcastState.data.list!![0]!!.dt_txt!!,
                                    "yyyy-MM-dd HH:mm:ss",
                                    "yyyy-MM-dd"
                                )!!
                            }
                            todayDateList = forcastState.data.list!!.filter { data ->
                                todayDate == dateCompare(
                                    data!!.dt_txt!!,
                                    "yyyy-MM-dd HH:mm:ss",
                                    "yyyy-MM-dd"
                                )
                            } as ArrayList<ForeCast3HoursModel.Data>

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 25.dp, vertical = 10.dp)
                                    .height(40.dp)
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember {
                                            MutableInteractionSource()
                                        }) {

                                        navController.navigate(Screen.DailyForecast.route)
                                    },
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Today",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp,
                                    color = Color.Black,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxHeight()
                                ) {
                                    Text(
                                        text = "Next  5  Days",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = skybluedark,
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically),
                                        textAlign = TextAlign.End
                                    )
                                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                                    Icon(
                                        Icons.Default.ArrowForwardIos,
                                        contentDescription = "",
                                        tint = skybluedark,
                                        modifier = Modifier
                                            .height(18.dp)
                                            .width(18.dp)
                                            .align(Alignment.CenterVertically)
                                    )
                                }
                            }
                            LazyRow(
                                Modifier
                                    .fillMaxWidth()
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                backgroundAllTheme,
                                                lightPurple.copy(alpha = 0.25f),
                                                backgroundAllTheme,
                                            )
                                        )
                                    )
                                    .padding(vertical = 15.dp),
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                item {
                                    Box(modifier = Modifier.width(1.dp)) {
                                    }
                                }
                                items(todayDateList.size) {

                                    var nerestTime = (dateCompare(
                                        todayDateList[it].dt_txt!!,
                                        "yyyy-MM-dd HH:mm:ss",
                                        "HH.ss"
                                    )!!.toFloat() > (todayTime - 1.5) &&
                                            dateCompare(
                                                todayDateList[it].dt_txt!!,
                                                "yyyy-MM-dd HH:mm:ss",
                                                "HH.ss"
                                            )!!.toFloat() < (todayTime + 1.5)
                                            )
                                    Card(
                                        shape = RoundedCornerShape(50.dp),
                                        elevation = if(nerestTime) 7.dp else 0.dp,
                                        modifier = Modifier
                                            .fillMaxHeight()
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.SpaceEvenly,
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier
                                                .background(
                                                    brush =
                                                    if (nerestTime) {
                                                        Brush.verticalGradient(
                                                            colors = listOf(
                                                                lightPurple,
                                                                darkPurple
                                                            )
                                                        )
                                                    } else {
                                                        Brush.verticalGradient(
                                                            colors = listOf(
                                                                Color.White,
                                                                Color.White
                                                            )
                                                        )
                                                    }
                                                )
                                                .height(125.dp)
                                                .width(68.dp)
                                                .padding(8.dp)
                                        ) {
                                            Text(
                                                text = convertDate(todayDateList[it].dt_txt!!)!!,
                                                color = if (nerestTime) {
                                                    Color.White
                                                } else {
                                                    Color.Gray.copy(alpha = 0.4f)
                                                },
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.End
                                            )
                                            Image(
                                                painter = painterResource(
                                                    id = when (todayDateList[it].weather!![0]!!.icon) {
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
                                                    .height((height * 0.04).dp)
                                                    .width((width * 0.08).dp),
                                                alignment = Alignment.TopCenter
                                            )
                                            Text(
                                                text = " ${((todayDateList[it].main!!.temp!! - 273.15)).toInt()}°",
                                                color = if (nerestTime) {
                                                    Color.White
                                                } else {
                                                    Color.Gray.copy(alpha = 0.4f)
                                                },
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.End
                                            )
                                        }

                                    }
                                }
                                item {
                                    Box(modifier = Modifier.width(1.dp)) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
            is CurrentWeatherStates.Error -> {

            }
        }
    }
}

@Composable
fun BottomShadow(alpha: Float = 0.1f, width: Dp = 100.dp,height: Dp = 8.dp,yOffset:Dp = 10.dp) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .padding(start = 5.dp, end = 4.dp)
            .offset(y=yOffset)
            .clip(shape = RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Gray.copy(alpha = alpha),
                        Color.Transparent,
                    )
                )
            )
    )
}