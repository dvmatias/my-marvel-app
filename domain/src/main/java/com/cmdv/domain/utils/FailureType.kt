package com.cmdv.domain.utils

sealed class FailureType {
	object ConnectionError : FailureType()
	object LocalError : FailureType()
	object ServerError : FailureType()
}