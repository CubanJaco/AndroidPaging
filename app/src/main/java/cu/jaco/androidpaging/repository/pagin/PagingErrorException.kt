package com.montbrungroup.androidpaguin.repository.pagin

import com.montbrungroup.androidpaguin.repository.safeapicall.ResultWrapper

class PagingErrorException(val result: ResultWrapper<Any, Any>): Exception()