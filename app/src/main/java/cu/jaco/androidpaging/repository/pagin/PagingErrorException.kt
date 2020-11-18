package cu.jaco.androidpaging.repository.pagin

import cu.jaco.androidpaging.repository.safeapicall.ResultWrapper

class PagingErrorException(val result: ResultWrapper<Any, Any>): Exception()