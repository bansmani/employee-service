package com.sg.employeeservice

import org.mockito.Mockito


//Note: mokito support for kotlin
 fun <T> any(type: Class<T>): T = Mockito.any(type)