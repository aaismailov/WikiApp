package com.example.wikiapp.ui.components.func

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class FormattedDateTime {

    private val daysOfWeekMap = mapOf(
        "Monday".uppercase() to "понедельник",
        "Tuesday".uppercase() to "вторник",
        "Wednesday".uppercase() to "среда",
        "Thursday".uppercase() to "четверг",
        "Friday".uppercase() to "пятница",
        "Saturday".uppercase() to "суббота",
        "Sunday".uppercase() to "воскресенье"
    )

    private val zoneGMPMap = mapOf(
        "Pacific/Niue" to "-11:00",
        "Pacific/Pago_Pago" to "-11:00",

        "Pacific/Honolulu" to "-10:00",
        "Pacific/Rarotonga" to "-10:00",
        "Pacific/Tahiti" to "-10:00",

        "Pacific/Marquesas" to "-09:30",

        "America/Anchorage" to "-09:00",
        "Pacific/Gambier" to "-09:00",

        "America/Los_Angeles" to "-08:00",
        "America/Tijuana" to "-08:00",
        "America/Vancouver" to "-08:00",
        "America/Whitehorse" to "-08:00",
        "Pacific/Pitcairn" to "-08:00",

        "America/Denver" to "-07:00",
        "America/Phoenix" to "-07:00",
        "America/Mazatlan" to "-07:00",
        "America/Dawson_Creek" to "-07:00",
        "America/Edmonton" to "-07:00",
        "America/Hermosillo" to "-07:00",
        "America/Yellowknife" to "-07:00",

        "America/Belize" to "-06:00",
        "America/Chicago" to "-06:00",
        "America/Mexico_City" to "-06:00",
        "America/Regina" to "-06:00",
        "America/Tegucigalpa" to "-06:00",
        "America/Winnipeg" to "-06:00",
        "America/Costa_Rica" to "-06:00",
        "America/El_Salvador" to "-06:00",
        "Pacific/Galapagos" to "-06:00",
        "America/Guatemala" to "-06:00",
        "America/Managua" to "-06:00",

        "America/Cancun" to "-05:00",
        "America/Bogota" to "-05:00",
        "Pacific/Easter" to "-05:00",
        "America/New_York" to "-05:00",
        "America/Iqaluit" to "-05:00",
        "America/Toronto" to "-05:00",
        "America/Guayaquil" to "-05:00",
        "America/Havana" to "-05:00",
        "America/Jamaica" to "-05:00",
        "America/Lima" to "-05:00",
        "America/Nassau" to "-05:00",
        "America/Panama" to "-05:00",
        "America/Port-au-Prince" to "-05:00",
        "America/Rio_Branco" to "-05:00",

        "America/Halifax" to "-04:00",
        "America/Barbados" to "-04:00",
        "Atlantic/Bermuda" to "-04:00",
        "America/Boa_Vista" to "-04:00",
        "America/Caracas" to "-04:00",
        "America/Curacao" to "-04:00",
        "America/Grand_Turk" to "-04:00",
        "America/Guyana" to "-04:00",
        "America/La_Paz" to "-04:00",
        "America/Manaus" to "-04:00",
        "America/Martinique" to "-04:00",
        "America/Port_of_Spain" to "-04:00",
        "America/Porto_Velho" to "-04:00",
        "America/Puerto_Rico" to "-04:00",
        "America/Santo_Domingo" to "-04:00",
        "America/Thule" to "-04:00",

        "America/St_Johns" to "-03:30",

        "America/Araguaina" to "-03:00",
        "America/Asuncion" to "-03:00",
        "America/Belem" to "-03:00",
        "America/Argentina/Buenos_Aires" to "-03:00",
        "America/Campo_Grande" to "-03:00",
        "America/Cayenne" to "-03:00",
        "America/Cuiaba" to "-03:00",
        "America/Fortaleza" to "-03:00",
        "America/Godthab" to "-03:00",
        "America/Maceio" to "-03:00",
        "America/Miquelon" to "-03:00",
        "America/Montevideo" to "-03:00",
        "Antarctica/Palmer" to "-03:00",
        "America/Paramaribo" to "-03:00",
        "America/Punta_Arenas" to "-03:00",
        "America/Recife" to "-03:00",
        "Antarctica/Rothera" to "-03:00",
        "America/Bahia" to "-03:00",
        "America/Santiago" to "-03:00",
        "Atlantic/Stanley" to "-03:00",

        "America/Noronha" to "-02:00",
        "America/Sao_Paulo" to "-02:00",
        "Atlantic/South_Georgia" to "-02:00",

        "Atlantic/Azores" to "-01:00",
        "Atlantic/Cape_Verde" to "-01:00",
        "America/Scoresbysund" to "-01:00",

        "Africa/Abidjan" to "+00:00",
        "Africa/Accra" to "+00:00",
        "Africa/Bissau" to "+00:00",
        "Atlantic/Canary" to "+00:00",
        "Africa/Casablanca" to "+00:00",
        "America/Danmarkshavn" to "+00:00",
        "Europe/Dublin" to "+00:00",
        "Africa/El_Aaiun" to "+00:00",
        "Atlantic/Faroe" to "+00:00",
        "Etc/GMT" to "+00:00",
        "Europe/Lisbon" to "+00:00",
        "Europe/London" to "+00:00",
        "Africa/Monrovia" to "+00:00",
        "Atlantic/Reykjavik" to "+00:00",

        "Africa/Algiers" to "+01:00",
        "Europe/Amsterdam" to "+01:00",
        "Europe/Andorra" to "+01:00",
        "Europe/Berlin" to "+01:00",
        "Europe/Brussels" to "+01:00",
        "Europe/Budapest" to "+01:00",
        "Europe/Belgrade" to "+01:00",
        "Europe/Prague" to "+01:00",
        "Africa/Ceuta" to "+01:00",
        "Europe/Copenhagen" to "+01:00",
        "Europe/Gibraltar" to "+01:00",
        "Africa/Lagos" to "+01:00",
        "Europe/Luxembourg" to "+01:00",
        "Europe/Madrid" to "+01:00",
        "Europe/Malta" to "+01:00",
        "Europe/Monaco" to "+01:00",
        "Africa/Ndjamena" to "+01:00",
        "Europe/Oslo" to "+01:00",
        "Europe/Paris" to "+01:00",
        "Europe/Rome" to "+01:00",
        "Europe/Stockholm" to "+01:00",
        "Europe/Tirane" to "+01:00",
        "Africa/Tunis" to "+01:00",
        "Europe/Vienna" to "+01:00",
        "Europe/Warsaw" to "+01:00",
        "Europe/Zurich" to "+01:00",

        "Asia/Amman" to "+02:00",
        "Europe/Athens" to "+02:00",
        "Asia/Beirut" to "+02:00",
        "Europe/Bucharest" to "+02:00",
        "Africa/Cairo" to "+02:00",
        "Europe/Chisinau" to "+02:00",
        "Asia/Damascus" to "+02:00",
        "Asia/Gaza" to "+02:00",
        "Europe/Helsinki" to "+02:00",
        "Asia/Jerusalem" to "+02:00",
        "Africa/Johannesburg" to "+02:00",
        "Africa/Khartoum" to "+02:00",
        "Europe/Kiev" to "+02:00",
        "Africa/Maputo" to "+02:00",
        "Europe/Kaliningrad" to "+02:00",
        "Asia/Nicosia" to "+02:00",
        "Europe/Riga" to "+02:00",
        "Europe/Sofia" to "+02:00",
        "Europe/Tallinn" to "+02:00",
        "Africa/Tripoli" to "+02:00",
        "Europe/Vilnius" to "+02:00",
        "Africa/Windhoek" to "+02:00",

        "Asia/Baghdad" to "+03:00",
        "Europe/Istanbul" to "+03:00",
        "Europe/Minsk" to "+03:00",
        "Europe/Moscow" to "+03:00",
        "Africa/Nairobi" to "+03:00",
        "Asia/Qatar" to "+03:00",
        "Asia/Riyadh" to "+03:00",
        "Antarctica/Syowa" to "+03:00",

        "Asia/Tehran" to "+03:30",

        "Asia/Baku" to "+04:00",
        "Asia/Dubai" to "+04:00",
        "Indian/Mahe" to "+04:00",
        "Indian/Mauritius" to "+04:00",
        "Europe/Samara" to "+04:00",
        "Indian/Reunion" to "+04:00",
        "Asia/Tbilisi" to "+04:00",
        "Asia/Yerevan" to "+04:00",

        "Asia/Kabul" to "+04:30",

        "Asia/Aqtau" to "+05:00",
        "Asia/Aqtobe" to "+05:00",
        "Asia/Ashgabat" to "+05:00",
        "Asia/Dushanbe" to "+05:00",
        "Asia/Karachi" to "+05:00",
        "Indian/Kerguelen" to "+05:00",
        "Indian/Maldives" to "+05:00",
        "Antarctica/Mawson" to "+05:00",
        "Asia/Yekaterinburg" to "+05:00",
        "Asia/Tashkent" to "+05:00",

        "Asia/Colombo" to "+05:30",
        "Asia/Kolkata" to "+05:30",

        "Asia/Kathmandu" to "+05:45",

        "Asia/Almaty" to "+06:00",
        "Asia/Bishkek" to "+06:00",
        "Indian/Chagos" to "+06:00",
        "Asia/Dhaka" to "+06:00",
        "Asia/Omsk" to "+06:00",
        "Asia/Thimphu" to "+06:00",
        "Antarctica/Vostok" to "+06:00",

        "Indian/Cocos" to "+06:30",
        "Asia/Yangon" to "+06:30",

        "Asia/Bangkok" to "+07:00",
        "Indian/Christmas" to "+07:00",
        "Antarctica/Davis" to "+07:00",
        "Asia/Saigon" to "+07:00",
        "Asia/Hovd" to "+07:00",
        "Asia/Jakarta" to "+07:00",
        "Asia/Krasnoyarsk" to "+07:00",

        "Asia/Brunei" to "+08:00",
        "Asia/Shanghai" to "+08:00",
        "Asia/Choibalsan" to "+08:00",
        "Asia/Hong_Kong" to "+08:00",
        "Asia/Kuala_Lumpur" to "+08:00",
        "Asia/Macau" to "+08:00",
        "Asia/Makassar" to "+08:00",
        "Asia/Manila" to "+08:00",
        "Asia/Irkutsk" to "+08:00",
        "Asia/Singapore" to "+08:00",
        "Asia/Taipei" to "+08:00",
        "Asia/Ulaanbaatar" to "+08:00",
        "Australia/Perth" to "+08:00",

        "Asia/Pyongyang" to "+08:30",

        "Asia/Dili" to "+09:00",
        "Asia/Jayapura" to "+09:00",
        "Asia/Yakutsk" to "+09:00",
        "Pacific/Palau" to "+09:00",
        "Asia/Seoul" to "+09:00",
        "Asia/Tokyo" to "+09:00",

        "Australia/Darwin" to "+09:30",

        "Antarctica/DumontDUrville" to "+10:00",
        "Australia/Brisbane" to "+10:00",
        "Pacific/Guam" to "+10:00",
        "Asia/Vladivostok" to "+10:00",
        "Pacific/Port_Moresby" to "+10:00",
        "Pacific/Chuuk" to "+10:00",

        "Australia/Adelaide" to "+10:30",

        "Antarctica/Casey" to "+11:00",
        "Australia/Hobart" to "+11:00",
        "Australia/Sydney" to "+11:00",
        "Pacific/Efate" to "+11:00",
        "Pacific/Guadalcanal" to "+11:00",
        "Pacific/Kosrae" to "+11:00",
        "Asia/Magadan" to "+11:00",
        "Pacific/Norfolk" to "+11:00",
        "Pacific/Noumea" to "+11:00",
        "Pacific/Pohnpei" to "+11:00",

        "Pacific/Funafuti" to "+12:00",
        "Pacific/Kwajalein" to "+12:00",
        "Pacific/Majuro" to "+12:00",
        "Asia/Kamchatka" to "+12:00",
        "Pacific/Nauru" to "+12:00",
        "Pacific/Tarawa" to "+12:00",
        "Pacific/Wake" to "+12:00",
        "Pacific/Wallis" to "+12:00",

        "Pacific/Auckland" to "+13:00",
        "Pacific/Enderbury" to "+13:00",
        "Pacific/Fakaofo" to "+13:00",
        "Pacific/Fiji" to "+13:00",
        "Pacific/Tongatapu" to "+13:00",

        "Pacific/Apia" to "+14:00",
        "Pacific/Kiritimati" to "+14:00",
    )

    private val zoneNameMap = mapOf(
        "Pacific/Niue" to "(GMT-11:00) Niue",
        "Pacific/Pago_Pago" to "(GMT-11:00) Pago Pago",

        "Pacific/Honolulu" to "(GMT-10:00) Hawaii Time",
        "Pacific/Rarotonga" to "(GMT-10:00) Rarotonga",
        "Pacific/Tahiti" to "(GMT-10:00) Tahiti",

        "Pacific/Marquesas" to "(GMT-09:30) Marquesas",

        "America/Anchorage" to "(GMT-09:00) Alaska Time",
        "Pacific/Gambier" to "(GMT-09:00) Gambier",

        "America/Los_Angeles" to "(GMT-08:00) Pacific Time",
        "America/Tijuana" to "(GMT-08:00) Pacific Time - Tijuana",
        "America/Vancouver" to "(GMT-08:00) Pacific Time - Vancouver",
        "America/Whitehorse" to "(GMT-08:00) Pacific Time - Whitehorse",
        "Pacific/Pitcairn" to "(GMT-08:00) Pitcairn",

        "America/Denver" to "(GMT-07:00) Mountain Time",
        "America/Phoenix" to "(GMT-07:00) Mountain Time - Arizona",
        "America/Mazatlan" to "(GMT-07:00) Mountain Time - Chihuahua, Mazatlan",
        "America/Dawson_Creek" to "(GMT-07:00) Mountain Time - Dawson Creek",
        "America/Edmonton" to "(GMT-07:00) Mountain Time - Edmonton",
        "America/Hermosillo" to "(GMT-07:00) Mountain Time - Hermosillo",
        "America/Yellowknife" to "(GMT-07:00) Mountain Time - Yellowknife",

        "America/Belize" to "(GMT-06:00) Belize",
        "America/Chicago" to "(GMT-06:00) Central Time",
        "America/Mexico_City" to "(GMT-06:00) Central Time - Mexico City",
        "America/Regina" to "(GMT-06:00) Central Time - Regina",
        "America/Tegucigalpa" to "(GMT-06:00) Central Time - Tegucigalpa",
        "America/Winnipeg" to "(GMT-06:00) Central Time - Winnipeg",
        "America/Costa_Rica" to "(GMT-06:00) Costa Rica",
        "America/El_Salvador" to "(GMT-06:00) El Salvador",
        "Pacific/Galapagos" to "(GMT-06:00) Galapagos",
        "America/Guatemala" to "(GMT-06:00) Guatemala",
        "America/Managua" to "(GMT-06:00) Managua",

        "America/Cancun" to "(GMT-05:00) America Cancun",
        "America/Bogota" to "(GMT-05:00) Bogota",
        "Pacific/Easter" to "(GMT-05:00) Easter Island",
        "America/New_York" to "(GMT-05:00) Eastern Time",
        "America/Iqaluit" to "(GMT-05:00) Eastern Time - Iqaluit",
        "America/Toronto" to "(GMT-05:00) Eastern Time - Toronto",
        "America/Guayaquil" to "(GMT-05:00) Guayaquil",
        "America/Havana" to "(GMT-05:00) Havana",
        "America/Jamaica" to "(GMT-05:00) Jamaica",
        "America/Lima" to "(GMT-05:00) Lima",
        "America/Nassau" to "(GMT-05:00) Nassau",
        "America/Panama" to "(GMT-05:00) Panama",
        "America/Port-au-Prince" to "(GMT-05:00) Port-au-Prince",
        "America/Rio_Branco" to "(GMT-05:00) Rio Branco",

        "America/Halifax" to "(GMT-04:00) Atlantic Time - Halifax",
        "America/Barbados" to "(GMT-04:00) Barbados",
        "Atlantic/Bermuda" to "(GMT-04:00) Bermuda",
        "America/Boa_Vista" to "(GMT-04:00) Boa Vista",
        "America/Caracas" to "(GMT-04:00) Caracas",
        "America/Curacao" to "(GMT-04:00) Curacao",
        "America/Grand_Turk" to "(GMT-04:00) Grand Turk",
        "America/Guyana" to "(GMT-04:00) Guyana",
        "America/La_Paz" to "(GMT-04:00) La Paz",
        "America/Manaus" to "(GMT-04:00) Manaus",
        "America/Martinique" to "(GMT-04:00) Martinique",
        "America/Port_of_Spain" to "(GMT-04:00) Port of Spain",
        "America/Porto_Velho" to "(GMT-04:00) Porto Velho",
        "America/Puerto_Rico" to "(GMT-04:00) Puerto Rico",
        "America/Santo_Domingo" to "(GMT-04:00) Santo Domingo",
        "America/Thule" to "(GMT-04:00) Thule",

        "America/St_Johns" to "(GMT-03:30) Newfoundland Time - St. Johns",

        "America/Araguaina" to "(GMT-03:00) Araguaina",
        "America/Asuncion" to "(GMT-03:00) Asuncion",
        "America/Belem" to "(GMT-03:00) Belem",
        "America/Argentina/Buenos_Aires" to "(GMT-03:00) Buenos Aires",
        "America/Campo_Grande" to "(GMT-03:00) Campo Grande",
        "America/Cayenne" to "(GMT-03:00) Cayenne",
        "America/Cuiaba" to "(GMT-03:00) Cuiaba",
        "America/Fortaleza" to "(GMT-03:00) Fortaleza",
        "America/Godthab" to "(GMT-03:00) Godthab",
        "America/Maceio" to "(GMT-03:00) Maceio",
        "America/Miquelon" to "(GMT-03:00) Miquelon",
        "America/Montevideo" to "(GMT-03:00) Montevideo",
        "Antarctica/Palmer" to "(GMT-03:00) Palmer",
        "America/Paramaribo" to "(GMT-03:00) Paramaribo",
        "America/Punta_Arenas" to "(GMT-03:00) Punta Arenas",
        "America/Recife" to "(GMT-03:00) Recife",
        "Antarctica/Rothera" to "(GMT-03:00) Rothera",
        "America/Bahia" to "(GMT-03:00) Salvador",
        "America/Santiago" to "(GMT-03:00) Santiago",
        "Atlantic/Stanley" to "(GMT-03:00) Stanley",

        "America/Noronha" to "(GMT-02:00) Noronha",
        "America/Sao_Paulo" to "(GMT-02:00) Sao Paulo",
        "Atlantic/South_Georgia" to "(GMT-02:00) South Georgia",

        "Atlantic/Azores" to "(GMT-01:00) Azores",
        "Atlantic/Cape_Verde" to "(GMT-01:00) Cape Verde",
        "America/Scoresbysund" to "(GMT-01:00) Scoresbysund",

        "Africa/Abidjan" to "(GMT+00:00) Abidjan",
        "Africa/Accra" to "(GMT+00:00) Accra",
        "Africa/Bissau" to "(GMT+00:00) Bissau",
        "Atlantic/Canary" to "(GMT+00:00) Canary Islands",
        "Africa/Casablanca" to "(GMT+00:00) Casablanca",
        "America/Danmarkshavn" to "(GMT+00:00) Danmarkshavn",
        "Europe/Dublin" to "(GMT+00:00) Dublin",
        "Africa/El_Aaiun" to "(GMT+00:00) El Aaiun",
        "Atlantic/Faroe" to "(GMT+00:00) Faroe",
        "Etc/GMT" to "(GMT+00:00) GMT (no daylight saving)",
        "Europe/Lisbon" to "(GMT+00:00) Lisbon",
        "Europe/London" to "(GMT+00:00) London",
        "Africa/Monrovia" to "(GMT+00:00) Monrovia",
        "Atlantic/Reykjavik" to "(GMT+00:00) Reykjavik",

        "Africa/Algiers" to "(GMT+01:00) Algiers",
        "Europe/Amsterdam" to "(GMT+01:00) Amsterdam",
        "Europe/Andorra" to "(GMT+01:00) Andorra",
        "Europe/Berlin" to "(GMT+01:00) Berlin",
        "Europe/Brussels" to "(GMT+01:00) Brussels",
        "Europe/Budapest" to "(GMT+01:00) Budapest",
        "Europe/Belgrade" to "(GMT+01:00) Central European Time - Belgrade",
        "Europe/Prague" to "(GMT+01:00) Central European Time - Prague",
        "Africa/Ceuta" to "(GMT+01:00) Ceuta",
        "Europe/Copenhagen" to "(GMT+01:00) Copenhagen",
        "Europe/Gibraltar" to "(GMT+01:00) Gibraltar",
        "Africa/Lagos" to "(GMT+01:00) Lagos",
        "Europe/Luxembourg" to "(GMT+01:00) Luxembourg",
        "Europe/Madrid" to "(GMT+01:00) Madrid",
        "Europe/Malta" to "(GMT+01:00) Malta",
        "Europe/Monaco" to "(GMT+01:00) Monaco",
        "Africa/Ndjamena" to "(GMT+01:00) Ndjamena",
        "Europe/Oslo" to "(GMT+01:00) Oslo",
        "Europe/Paris" to "(GMT+01:00) Paris",
        "Europe/Rome" to "(GMT+01:00) Rome",
        "Europe/Stockholm" to "(GMT+01:00) Stockholm",
        "Europe/Tirane" to "(GMT+01:00) Tirane",
        "Africa/Tunis" to "(GMT+01:00) Tunis",
        "Europe/Vienna" to "(GMT+01:00) Vienna",
        "Europe/Warsaw" to "(GMT+01:00) Warsaw",
        "Europe/Zurich" to "(GMT+01:00) Zurich",

        "Asia/Amman" to "(GMT+02:00) Amman",
        "Europe/Athens" to "(GMT+02:00) Athens",
        "Asia/Beirut" to "(GMT+02:00) Beirut",
        "Europe/Bucharest" to "(GMT+02:00) Bucharest",
        "Africa/Cairo" to "(GMT+02:00) Cairo",
        "Europe/Chisinau" to "(GMT+02:00) Chisinau",
        "Asia/Damascus" to "(GMT+02:00) Damascus",
        "Asia/Gaza" to "(GMT+02:00) Gaza",
        "Europe/Helsinki" to "(GMT+02:00) Helsinki",
        "Asia/Jerusalem" to "(GMT+02:00) Jerusalem",
        "Africa/Johannesburg" to "(GMT+02:00) Johannesburg",
        "Africa/Khartoum" to "(GMT+02:00) Khartoum",
        "Europe/Kiev" to "(GMT+02:00) Kiev",
        "Africa/Maputo" to "(GMT+02:00) Maputo",
        "Europe/Kaliningrad" to "(GMT+02:00) Moscow-01 - Kaliningrad",
        "Asia/Nicosia" to "(GMT+02:00) Nicosia",
        "Europe/Riga" to "(GMT+02:00) Riga",
        "Europe/Sofia" to "(GMT+02:00) Sofia",
        "Europe/Tallinn" to "(GMT+02:00) Tallinn",
        "Africa/Tripoli" to "(GMT+02:00) Tripoli",
        "Europe/Vilnius" to "(GMT+02:00) Vilnius",
        "Africa/Windhoek" to "(GMT+02:00) Windhoek",

        "Asia/Baghdad" to "(GMT+03:00) Baghdad",
        "Europe/Istanbul" to "(GMT+03:00) Istanbul",
        "Europe/Minsk" to "(GMT+03:00) Minsk",
        "Europe/Moscow" to "(GMT+03:00) Moscow+00 - Moscow",
        "Africa/Nairobi" to "(GMT+03:00) Nairobi",
        "Asia/Qatar" to "(GMT+03:00) Qatar",
        "Asia/Riyadh" to "(GMT+03:00) Riyadh",
        "Antarctica/Syowa" to "(GMT+03:00) Syowa",

        "Asia/Tehran" to "(GMT+03:30) Tehran",

        "Asia/Baku" to "(GMT+04:00) Baku",
        "Asia/Dubai" to "(GMT+04:00) Dubai",
        "Indian/Mahe" to "(GMT+04:00) Mahe",
        "Indian/Mauritius" to "(GMT+04:00) Mauritius",
        "Europe/Samara" to "(GMT+04:00) Moscow+01 - Samara",
        "Indian/Reunion" to "(GMT+04:00) Reunion",
        "Asia/Tbilisi" to "(GMT+04:00) Tbilisi",
        "Asia/Yerevan" to "(GMT+04:00) Yerevan",

        "Asia/Kabul" to "(GMT+04:30) Kabul",

        "Asia/Aqtau" to "(GMT+05:00) Aqtau",
        "Asia/Aqtobe" to "(GMT+05:00) Aqtobe",
        "Asia/Ashgabat" to "(GMT+05:00) Ashgabat",
        "Asia/Dushanbe" to "(GMT+05:00) Dushanbe",
        "Asia/Karachi" to "(GMT+05:00) Karachi",
        "Indian/Kerguelen" to "(GMT+05:00) Kerguelen",
        "Indian/Maldives" to "(GMT+05:00) Maldives",
        "Antarctica/Mawson" to "(GMT+05:00) Mawson",
        "Asia/Yekaterinburg" to "(GMT+05:00) Moscow+02 - Yekaterinburg",
        "Asia/Tashkent" to "(GMT+05:00) Tashkent",

        "Asia/Colombo" to "(GMT+05:30) Colombo",
        "Asia/Kolkata" to "(GMT+05:30) India Standard Time",

        "Asia/Kathmandu" to "(GMT+05:45) Kathmandu",

        "Asia/Almaty" to "(GMT+06:00) Almaty",
        "Asia/Bishkek" to "(GMT+06:00) Bishkek",
        "Indian/Chagos" to "(GMT+06:00) Chagos",
        "Asia/Dhaka" to "(GMT+06:00) Dhaka",
        "Asia/Omsk" to "(GMT+06:00) Moscow+03 - Omsk",
        "Asia/Thimphu" to "(GMT+06:00) Thimphu",
        "Antarctica/Vostok" to "(GMT+06:00) Vostok",

        "Indian/Cocos" to "(GMT+06:30) Cocos",
        "Asia/Yangon" to "(GMT+06:30) Rangoon",

        "Asia/Bangkok" to "(GMT+07:00) Bangkok",
        "Indian/Christmas" to "(GMT+07:00) Christmas",
        "Antarctica/Davis" to "(GMT+07:00) Davis",
        "Asia/Saigon" to "(GMT+07:00) Hanoi",
        "Asia/Hovd" to "(GMT+07:00) Hovd",
        "Asia/Jakarta" to "(GMT+07:00) Jakarta",
        "Asia/Krasnoyarsk" to "(GMT+07:00) Moscow+04 - Krasnoyarsk",

        "Asia/Brunei" to "(GMT+08:00) Brunei",
        "Asia/Shanghai" to "(GMT+08:00) China Time - Beijing",
        "Asia/Choibalsan" to "(GMT+08:00) Choibalsan",
        "Asia/Hong_Kong" to "(GMT+08:00) Hong Kong",
        "Asia/Kuala_Lumpur" to "(GMT+08:00) Kuala Lumpur",
        "Asia/Macau" to "(GMT+08:00) Macau",
        "Asia/Makassar" to "(GMT+08:00) Makassar",
        "Asia/Manila" to "(GMT+08:00) Manila",
        "Asia/Irkutsk" to "(GMT+08:00) Moscow+05 - Irkutsk",
        "Asia/Singapore" to "(GMT+08:00) Singapore",
        "Asia/Taipei" to "(GMT+08:00) Taipei",
        "Asia/Ulaanbaatar" to "(GMT+08:00) Ulaanbaatar",
        "Australia/Perth" to "(GMT+08:00) Western Time - Perth",

        "Asia/Pyongyang" to "(GMT+08:30) Pyongyang",

        "Asia/Dili" to "(GMT+09:00) Dili",
        "Asia/Jayapura" to "(GMT+09:00) Jayapura",
        "Asia/Yakutsk" to "(GMT+09:00) Moscow+06 - Yakutsk",
        "Pacific/Palau" to "(GMT+09:00) Palau",
        "Asia/Seoul" to "(GMT+09:00) Seoul",
        "Asia/Tokyo" to "(GMT+09:00) Tokyo",

        "Australia/Darwin" to "(GMT+09:30) Central Time - Darwin",

        "Antarctica/DumontDUrville" to "(GMT+10:00) Dumont D'Urville",
        "Australia/Brisbane" to "(GMT+10:00) Eastern Time - Brisbane",
        "Pacific/Guam" to "(GMT+10:00) Guam",
        "Asia/Vladivostok" to "(GMT+10:00) Moscow+07 - Vladivostok",
        "Pacific/Port_Moresby" to "(GMT+10:00) Port Moresby",
        "Pacific/Chuuk" to "(GMT+10:00) Truk",

        "Australia/Adelaide" to "(GMT+10:30) Central Time - Adelaide",

        "Antarctica/Casey" to "(GMT+11:00) Casey",
        "Australia/Hobart" to "(GMT+11:00) Eastern Time - Hobart",
        "Australia/Sydney" to "(GMT+11:00) Eastern Time - Melbourne, Sydney",
        "Pacific/Efate" to "(GMT+11:00) Efate",
        "Pacific/Guadalcanal" to "(GMT+11:00) Guadalcanal",
        "Pacific/Kosrae" to "(GMT+11:00) Kosrae",
        "Asia/Magadan" to "(GMT+11:00) Moscow+08 - Magadan",
        "Pacific/Norfolk" to "(GMT+11:00) Norfolk",
        "Pacific/Noumea" to "(GMT+11:00) Noumea",
        "Pacific/Pohnpei" to "(GMT+11:00) Ponape",

        "Pacific/Funafuti" to "(GMT+12:00) Funafuti",
        "Pacific/Kwajalein" to "(GMT+12:00) Kwajalein",
        "Pacific/Majuro" to "(GMT+12:00) Majuro",
        "Asia/Kamchatka" to "(GMT+12:00) Moscow+09 - Petropavlovsk-Kamchatskiy",
        "Pacific/Nauru" to "(GMT+12:00) Nauru",
        "Pacific/Tarawa" to "(GMT+12:00) Tarawa",
        "Pacific/Wake" to "(GMT+12:00) Wake",
        "Pacific/Wallis" to "(GMT+12:00) Wallis",

        "Pacific/Auckland" to "(GMT+13:00) Auckland",
        "Pacific/Enderbury" to "(GMT+13:00) Enderbury",
        "Pacific/Fakaofo" to "(GMT+13:00) Fakaofo",
        "Pacific/Fiji" to "(GMT+13:00) Fiji",
        "Pacific/Tongatapu" to "(GMT+13:00) Tongatapu",

        "Pacific/Apia" to "(GMT+14:00) Apia",
        "Pacific/Kiritimati" to "(GMT+14:00) Kiritimati",
    )

    fun getZoneGMPMap(): Map<String, String> {
        return zoneGMPMap
    }

    fun getZoneNameMap(): Map<String, String> {
        return zoneNameMap
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formattedDate(d: String, dFormat: String = "", isPages: Boolean): String {
        var newFormat = ""
        if (dFormat.isNotEmpty()) {
            newFormat = dFormat.replace('Y', 'y', ignoreCase = true)
                .replace('D', 'd', ignoreCase = true)
        }

        val secondApiFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale("ru")
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }
//        val dateParser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val dateParser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        var dateParsed = listOf("")
        var dayOfWeek = ""
        val date1 = LocalDate.parse(d, secondApiFormat)
        val date2 = dateParser.parse(d)

        val dateFormatter = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale("ru"))
        val dateFormatter2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
        val formattedDate = dateFormatter.format(date2!!)
        dateParsed = formattedDate.split(' ')

        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val current = formatter.format(time)

//        Log.d("FormattedDate 2", dateFormatter2.parse(d).toString())
        val formatter2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
//        formatter2.timeZone = TimeZone.getTimeZone("Pacific/Honolulu") // 00:58
//        formatter2.timeZone = TimeZone.getTimeZone("America/Anchorage") // 23:
//        formatter2.timeZone = TimeZone.getTimeZone("Europe/Moscow") // 23:
     //   Log.d("FormattedDate key", key)
//        formatter2.timeZone = TimeZone.getTimeZone(zoneGMPMap[key]?.replace('+', '-') ?: "") // 23:
        formatter2.timeZone = TimeZone.getTimeZone("Europe/Moscow") // 23:
        val result = formatter2.parse(d)
        Log.d("FormattedDate result", result.toString())

        Log.d("FormattedDate date1", date1.toString())
        Log.d("FormattedDate date2", date2.toString())


        Log.d("FormattedDate time", time.toString())
        Log.d("FormattedDate current", current)
//        val diff = date2.time - time
//        val seconds = diff / 1000
//        val minutes = seconds / 60
//        val hours = minutes / 60
//        val days = hours / 24

        val currentDate = Date()
        Log.d("FormattedDate currentDa", currentDate.toString())


        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val myDate = simpleDateFormat.parse(d)
        if (myDate != null) {
            Log.d("FormattedDate myDate", myDate.toString())
        }

            val newDate: String
        if (!isPages) {
            for ((key, value) in daysOfWeekMap) {
                if (key == date1.dayOfWeek.toString())
                    dayOfWeek = value
            }
            newDate = "$dayOfWeek, ${dateParsed[0]} ${dateParsed[1]}" +
                    " ${dateParsed[2]} г., " + dateParsed[3]
        } else {

            newDate = SimpleDateFormat(newFormat, Locale("ru")).format(date2)
            Log.d("FormattedDate newDate",newDate)
//            newDate = SimpleDateFormat(newFormat, Locale("ru")).format(result)
        }
        return newDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formattedByTimezone(date: String, curTimezone: String): String {
        var offsetId = ""
        for ((key, value) in zoneGMPMap) {
            if (curTimezone == key)
                offsetId = value
        }
        val offsetTime: OffsetTime = OffsetTime.of(LocalTime.parse(date), ZoneOffset.UTC)
            .withOffsetSameInstant(ZoneOffset.of(offsetId))
        val result: LocalTime = offsetTime.toLocalTime()
        return result.toString()
    }

    fun getDateWithoutTime(date: String): String {
        val newDate: List<String> = date.split(' ')
        var dateWithoutTime = ""
        newDate.forEachIndexed { index, s ->
            if (index in 0..4)
                dateWithoutTime += "$s "
        }
        return dateWithoutTime
    }

    fun getTimeWithoutDate(date: String): String {
        val newDate: List<String> = date.split(' ')
        return newDate[newDate.lastIndex]
    }
}