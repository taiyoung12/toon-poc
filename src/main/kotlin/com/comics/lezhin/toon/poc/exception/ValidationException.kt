package com.comics.lezhin.toon.poc.exception

import com.comics.lezhin.toon.poc.common.CommonCode

class ValidationException(
    val errors: Map<String, String>,
) : BaseException(CommonCode.VALIDATION_ERROR)
