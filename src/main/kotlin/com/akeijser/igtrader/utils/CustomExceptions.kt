package com.akeijser.igtrader.utils

class IGSessionExceptions(httpStatusCode: Int) : Exception("Failed to create IG Session. status code: $httpStatusCode")