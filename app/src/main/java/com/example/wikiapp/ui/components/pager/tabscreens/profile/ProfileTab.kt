package com.example.wikiapp.ui.components.pager.tabscreens.profile

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wikiapp.R
import com.example.wikiapp.ui.components.divider.DividerProfile
import com.example.wikiapp.ui.components.func.FormattedDateTime
import com.example.wikiapp.ui.components.pager.item.ContentRowWithImage
import com.example.wikiapp.ui.components.pager.item.ContentRowWithoutImage
import com.example.wikiapp.ui.components.pager.item.HeaderRow
import com.example.wikiapp.ui.components.pager.item.MainTitleWithButton
import com.example.wikiapp.ui.screens.profile.ProfileViewModel
import com.example.wikiapp.ui.theme.*

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileTab(
    userName: String = "User Name",
    providerName: String = "Provider Name",
    location: String = "Location",
    jobTitle: String = "Job Title",
    timezone: String = "Timezone",
    dateFormat: String = "Date Format",
    appearance: String = "Appearance",
    createdAt: String = "Created At",
    updatedAt: String = "Updated At",
    lastLoginAt: String = "Last Login At",
    groups: List<String?>?,
    pagesTotal: String = "Pages Total",
    commentsTotal: String = "Comments Total"
) {
    val shape = RoundedCornerShape(7.dp)
    val scrollState = rememberScrollState()
    val fdt = FormattedDateTime()

    val viewModel: ProfileViewModel = viewModel()
    val getUsersProfileResult by viewModel.getUsersProfileResult.collectAsState()
    val currentTimezone = getUsersProfileResult.data?.data?.users?.profile?.timezone

    val headerInfo = "Моя информация"
    val headerAuth = "Аутентификация"
    val headerSettings = "Настройки"
    val headerGroups = "Группы"
    val headerActivity = "Активность"

    val titlePersonalInfo = "Моя личная информация"
    val titleName = "Отображаемое имя"
    val titleLocation = "Расположение"
    val titleJob = "Должность"
    val titleTimezone = "Часовой пояс"
    val titleDateFormat = "Формат даты"
    val titleDefaultDateFormat = "Формат по умолчанию"
    val titleAppearance = "Внешний вид"
    val titleAppearanceLightEng = "light"
    val titleAppearanceDarkEng = "dark"
    val titleAppearanceLightRus = "Светлый режим"
    val titleAppearanceDarkRus = "Темный режим"
    val titleAppearanceDefault = "Сайт по умолчанию"
    val titleJoined = "Присоединился"
    val titleLastUpdate = "Последнее обновление профиля"
    val titleLastEntrance = "Последний вход в систему"
    val titlePagesCreated = "Страницы созданы"
    val titleCommentsPublished = "Опубликованных комментариев"

    Column(
        modifier = Modifier
            .background(lightGray)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp)
    ) {
        MainTitleWithButton(
            idImg = R.drawable.check,
            title = titlePersonalInfo,
            isButtonSave = false
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .clip(shape)
        ) {
            Box(
                modifier = Modifier
                    .background(lightGray)
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(
                        header = headerInfo,
                        backgroundColor = middleGray
                    )
                    ContentRowWithImage(
                        idImg = R.drawable.ic_person,
                        title = titleName,
                        subtitle = userName,
                        isBtn = true
                    )
                    DividerProfile()
                    ContentRowWithImage(
                        idImg = R.drawable.map_marker,
                        title = titleLocation,
                        subtitle = if (location == "-") "" else location,
                        isBtn = true
                    )
                    DividerProfile()
                    ContentRowWithImage(
                        idImg = R.drawable.briefcase,
                        title = titleJob,
                        subtitle = if (jobTitle == "-") "" else jobTitle,
                        isBtn = true
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .clip(shape)
        ) {
            Box(
                modifier = Modifier
                    .background(lightGray)
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(
                        header = headerAuth,
                        backgroundColor = middleGray
                    )
                    ContentRowWithImage(
                        idImg = R.drawable.shield_lock,
                        title = providerName,
                        subtitle = "",
                        isBtn = false
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .clip(shape)
        ) {
            Box(
                modifier = Modifier
                    .background(lightGray)
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(
                        header = headerSettings,
                        backgroundColor = middleGray
                    )

                    ContentRowWithImage(
                        idImg = R.drawable.map_clock_outline,
                        title = titleTimezone,
                        subtitle = timezone,
                        isBtn = true
                    )
                    DividerProfile()
                    ContentRowWithImage(
                        idImg = R.drawable.calendar_month_outline,
                        title = titleDateFormat,
                        subtitle = if (dateFormat == "")
                            titleDefaultDateFormat else dateFormat,
                        isBtn = true
                    )
                    DividerProfile()
                    ContentRowWithImage(
                        idImg = R.drawable.palette,
                        title = titleAppearance,
                        subtitle =
                        when (appearance) {
                            titleAppearanceLightEng -> titleAppearanceLightRus
                            titleAppearanceDarkEng -> titleAppearanceDarkRus
                            else -> {
                                titleAppearanceDefault
                            }
                        },
                        isBtn = true
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .clip(shape)
        ) {
            Box(
                modifier = Modifier
                    .background(lightGray)
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(
                        header = headerGroups,
                        backgroundColor = brightBlue
                    )
                    groups?.forEach { i ->
                        i?.let {
                            ContentRowWithImage(
                                idImg = R.drawable.account_group,
                                title = it,
                                subtitle = "",
                                isBtn = false
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .clip(shape)
        ) {
            Box(
                modifier = Modifier
                    .background(lightGray)
                    .fillMaxWidth()
            ) {
                Column {
                    HeaderRow(
                        header = headerActivity,
                        backgroundColor = turquoise
                    )
                    ContentRowWithoutImage(
                        title = titleJoined,
                        subtitle = if (createdAt.isNotEmpty()) {
                            fdt.getDateWithoutTime(fdt.formattedDate(createdAt, "", false)) +
                                    fdt.formattedByTimezone(
                                        date = fdt.getTimeWithoutDate(
                                            fdt.formattedDate(
                                                createdAt,
                                                "",
                                                false
                                            )
                                        ),
                                        curTimezone = currentTimezone.toString()
                                    )
                        } else ""
                    )
                    ContentRowWithoutImage(
                        title = titleLastUpdate,
                        subtitle = if (updatedAt.isNotEmpty()) {
                            fdt.getDateWithoutTime(fdt.formattedDate(updatedAt, "", false)) +
                                    fdt.formattedByTimezone(
                                        date = fdt.getTimeWithoutDate(
                                            fdt.formattedDate(
                                                updatedAt,
                                                "",
                                                false
                                            )
                                        ),
                                        curTimezone = currentTimezone.toString()
                                    )
                        } else ""
                    )
                    ContentRowWithoutImage(
                        title = titleLastEntrance,
                        subtitle = if (lastLoginAt.isNotEmpty()) {
                            fdt.getDateWithoutTime(fdt.formattedDate(lastLoginAt, "", false)) +
                                    fdt.formattedByTimezone(
                                        date = fdt.getTimeWithoutDate(
                                            fdt.formattedDate(
                                                lastLoginAt,
                                                "",
                                                false
                                            )
                                        ),
                                        curTimezone = currentTimezone.toString()
                                    )
                        } else ""
                    )
                    DividerProfile()
                    ContentRowWithoutImage(
                        title = titlePagesCreated,
                        subtitle = pagesTotal
                    )
                    ContentRowWithoutImage(
                        title = titleCommentsPublished,
                        subtitle = commentsTotal
                    )
                }
            }
        }

    }
}