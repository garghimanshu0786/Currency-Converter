package com.himanshu.currencyconverter

import com.himanshu.currencyconverter.features.domain.usecases.AmountFilterUseCase
import org.junit.Test

import org.junit.Assert.*
import org.junit.jupiter.api.Assertions
import java.lang.StringBuilder

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
	@Test
	fun addition_isCorrect() {
		assertEquals("a4b2c3a1b2", compress("aaaabbcccabb"))
	}

	// input : "aaaabbcccab"
// output : "a4b2c3a1b1"

	fun compress(text: String): String { //aaaabbcccab
		val sb = StringBuilder();
		if (text.isBlank()){
			return "-1"
		}
		var count = 1                       // 1, 2, 3
//		sb.append(text[0])
		for (i in 1 until text.length){
			println("himanshu" + i)
//			if (text[i] == text[i - 1]) {
//				count++
//			} else {
//				sb.append(text[i - 1])
//				sb.append(count)
//				count = 1
//			}
		}

//		sb.append(text[text.length - 1])
//		sb.append(count)

		return  sb.toString()
	}

}