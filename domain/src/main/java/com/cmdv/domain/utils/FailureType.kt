package com.cmdv.domain.utils

sealed class FailureType {
	object ConnectionError : FailureType()
	object ServerError : FailureType()
	object LocalError : FailureType()
	object ResponseTransformError : FailureType()
}