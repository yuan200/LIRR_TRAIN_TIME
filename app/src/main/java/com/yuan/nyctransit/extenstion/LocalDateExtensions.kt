package com.yuan.nyctransit.extenstion

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

fun LocalDate.toDateString() = this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

