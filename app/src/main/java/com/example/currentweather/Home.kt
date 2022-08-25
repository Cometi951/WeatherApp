package com.example.currentweather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.example.currentweather.model.ForeCast3HoursModel
import com.example.currentweather.ui.theme.*
import com.example.currentweather.utils.convertDate
import com.example.currentweather.utils.dateCompare
import com.example.currentweather.utils.getmDate
import com.example.currentweather.viewModel.CurrentWeatherStates
import com.example.currentweather.viewModel.Forecast3HoursStates
import com.example.currentweather.viewModel.MainViewModel
import com.example.currentweather.screen.Screen
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun Home(viewModel: MainViewModel, navController: NavHostController) {

    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp
    var todayDate: String = ""
    var todayDateList: ArrayList<ForeCast3HoursModel.Data> = ArrayList()

    Scaffold(
    ) {

        when (val state = viewModel.currentWeather.collectAsState().value) {
            is CurrentWeatherStates.Loading -> {

                Box( Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }
            is CurrentWeatherStates.Error -> {

                Box( Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = state.message)
                }
            }
            is CurrentWeatherStates.Loaded -> {

                Column(Modifier.padding(top = 15.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((height * 0.25).dp)
                            .padding(top = 10.dp, end = 25.dp, start = 25.dp),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        val time =

                            Column() {
                                Text(
                                    text = state.data.name!!,
                                    fontSize = 35.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = getmDate(state.data.dt!!.toLong())!!,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray,
                                    fontSize = 15.sp
                                )
                            }

                        Card(elevation = 1.dp, shape = RoundedCornerShape(30.dp), modifier = Modifier
                            .wrapContentSize()
                            .padding(30.dp), backgroundColor = Color.Transparent) {
                            Image(
                                modifier = Modifier,
                                painter = painterResource(id = R.drawable.maps),
                                contentDescription = "",
                            )
                        }

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
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 35.dp)
                                    .height((height * 0.20).dp)
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
                                            top = 10.dp,
                                            bottom = 15.dp,
                                            start = 20.dp,
                                            end = 20.dp
                                        )
                                        .fillMaxSize(),
                                ) {
                                    Text(
                                        "${state.data.weather!![0]!!.description}".uppercase(
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
                                                    "${((state.data.main!!.temp!! - 273.15)).toInt()}"
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
                                                    "Feels LIke ${((state.data.main!!.feels_like!! - 273.15)).toInt()}"
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
                                                lightblue
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
                                                lightblue
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
                                                lightblue
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
                            Box( Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                CircularProgressIndicator()
                            }
                        }

                        is Forecast3HoursStates.Error -> {

                            Box( Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                Text(text = forcastState.message)
                            }
                        }

                        is Forecast3HoursStates.Loaded -> {

                            if (!todayDate.isNotBlank()) {
                                todayDate = dateCompare(forcastState.data.list!![0]!!.dt_txt!!,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd")!!
                            }
                            todayDateList = forcastState.data.list!!.filter { data ->
                                todayDate == dateCompare(data!!.dt_txt!!, "yyyy-MM-dd HH:mm:ss","yyyy-MM-dd")
                            } as ArrayList<ForeCast3HoursModel.Data>


                            Row(
                                modifier = Modifier
                                    .fillMaxWidth().padding(horizontal = 25.dp, vertical = 10.dp),
                                Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Today",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )

                                Text(
                                    text = "Next  5  Days  >",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = skybluedark,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .clickable(indication = null, interactionSource = remember {
                                            MutableInteractionSource()
                                        }) {

                                            navController.navigate(Screen.DailyForecast.route)
                                        },
                                    textAlign = TextAlign.End
                                )
                            }
                            LazyRow(
                                Modifier
                                    .fillMaxWidth()
                                    .height(170.dp)
                                    .padding(vertical = 15.dp),
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                item {
                                    Box(modifier = Modifier.width(1.dp)) {
                                    }
                                }
                                items(todayDateList.size) {
                                    Card(
                                        shape = RoundedCornerShape(50.dp),
                                        elevation = 5.dp,
                                        modifier = Modifier
                                            .fillMaxHeight(),
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.SpaceEvenly,
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier
                                                .background(
                                                    brush = Brush.verticalGradient(
                                                        colors = listOf(
                                                            lightPurple,
                                                            darkPurple
                                                        )
                                                    )
                                                )
                                                .width(70.dp)
                                                .padding(10.dp)
                                        ) {
                                            Text(
                                                text = convertDate(todayDateList[it].dt_txt!!)!!,
                                                color = Color.White,
                                                fontSize = 14.sp,
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
                                                    .height((height * 0.05).dp)
                                                    .width((width * 0.08).dp),
                                                alignment = Alignment.TopCenter
                                            )
                                            Text(
                                                text = buildAnnotatedString {
                                                    append(
                                                        "${((todayDateList[it].main!!.temp!! - 273.15)).toInt()}"
                                                    )
                                                    withStyle(
                                                        SpanStyle(
                                                            baselineShift = BaselineShift.Superscript,
                                                            color = Color.White,
                                                            fontSize = 12.sp
                                                        )
                                                    ) {
                                                        append("o")
                                                    }
                                                }, color = Color.White,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.End
                                            )
                                        }

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