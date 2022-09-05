package denshchikov.dmitry.comparator

import com.fasterxml.jackson.databind.ObjectMapper
import org.http4k.core.Response
import org.http4k.core.Status
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.lang.NullPointerException
import kotlin.test.assertFailsWith

internal class ResponseComparatorTest {

    private val om = ObjectMapper()

    private val responseSample = """
        {
            "id": "123456789",
            "testNestedObj": {
                "nestedId": 123
            },
            "testArray": [
                {
                    "id": 1,
                    "value": "test1"
                },
                {
                    "id": 2,
                    "value": "test2"
                }
            ]
        }
    """

    @Test
    fun `Ensure that two responses are equal`() {
        val responseComparator = ResponseComparator(om)

        val responseSampleForCompare = """
            {
                "id": "123456789",
                "testNestedObj": {
                    "nestedId": 123
                },
                "testArray": [
                    {
                        "id": 1,
                        "value": "test1"
                    },
                    {
                        "id": 2,
                        "value": "test2"
                    }
                ]
            }
        """
        val r2 = Response(Status.OK).body(responseSampleForCompare)
        val r1 = Response(Status.OK).body(responseSample)

        assertTrue(responseComparator.compare(r1, r2))
    }

    @Test
    fun `Ensure that two responses are equal except of ignored field`() {
        val responseComparator = ResponseComparator(om, listOf("id"))

        val responseSampleForCompare = """
            {
                "id": "12345678",
                "testNestedObj": {
                    "nestedId": 123
                },
                "testArray": [
                    {
                        "id": 1,
                        "value": "test1"
                    },
                    {
                        "id": 2,
                        "value": "test2"
                    }
                ]
            }
        """
        val r2 = Response(Status.OK).body(responseSampleForCompare)
        val r1 = Response(Status.OK).body(responseSample)

        assertTrue(responseComparator.compare(r1, r2))
    }

    @Test
    fun `Ensure that two responses are equal except of ignored field in nested object`() {
        val responseComparator = ResponseComparator(om, listOf("testNestedObj.nestedId"))

        val responseSampleForCompare = """
            {
                "id": "123456789",
                "testNestedObj": {
                    "nestedId": 12
                },
                "testArray": [
                    {
                        "id": 1,
                        "value": "test1"
                    },
                    {
                        "id": 2,
                        "value": "test2"
                    }
                ]
            }
        """
        val r2 = Response(Status.OK).body(responseSampleForCompare)
        val r1 = Response(Status.OK).body(responseSample)

        assertTrue(responseComparator.compare(r1, r2))
    }


    @Test
    fun `Ensure that two responses are not equal`() {
        val responseComparator = ResponseComparator(om)

        val responseSampleForCompare = """
            {
                "id": "12345678",
                "testNestedObj": {
                    "nestedId": 123
                },
                "testArray": [
                    {
                        "id": 1,
                        "value": "test1"
                    },
                    {
                        "id": 2,
                        "value": "test2"
                    }
                ]
            }
        """
        val r2 = Response(Status.OK).body(responseSampleForCompare)
        val r1 = Response(Status.OK).body(responseSample)

        assertFalse(responseComparator.compare(r1, r2))
    }

    @Test
    fun `Ensure that two responses are not equal except of some ignored fields`() {
        val responseComparator = ResponseComparator(om, listOf("testNestedObj.nestedId"))

        val responseSampleForCompare = """
            {
                "id": "12345678",
                "testNestedObj": {
                    "nestedId": 123
                },
                "testArray": [
                    {
                        "id": 1,
                        "value": "test1"
                    },
                    {
                        "id": 2,
                        "value": "test2"
                    }
                ]
            }
        """
        val r2 = Response(Status.OK).body(responseSampleForCompare)
        val r1 = Response(Status.OK).body(responseSample)

        assertFalse(responseComparator.compare(r1, r2))
    }

    @Test
    fun `Ensure failure if there is an nonexistent property in ignored fields with incorrect depth`() {
        val responseComparator = ResponseComparator(om, listOf("testNestedObj.nested.nonexistentProperty"))

        val responseSampleForCompare = """
            {
                "id": "123456789",
                "testNestedObj": {
                    "nestedId": 123
                },
                "testArray": [
                    {
                        "id": 1,
                        "value": "test1"
                    },
                    {
                        "id": 2,
                        "value": "test2"
                    }
                ]
            }
        """
        val r2 = Response(Status.OK).body(responseSampleForCompare)
        val r1 = Response(Status.OK).body(responseSample)

        assertFailsWith<NullPointerException>{
            responseComparator.compare(r1, r2)
        }
    }

    @Test
    fun `Ensure that two responses are not equal if there is an nonexistent property in ignored fields`() {
        val responseComparator = ResponseComparator(om, listOf("testNestedObj.nested"))

        val responseSampleForCompare = """
            {
                "id": "12345678",
                "testNestedObj": {
                    "nestedId": 123
                },
                "testArray": [
                    {
                        "id": 1,
                        "value": "test1"
                    },
                    {
                        "id": 2,
                        "value": "test2"
                    }
                ]
            }
        """
        val r2 = Response(Status.OK).body(responseSampleForCompare)
        val r1 = Response(Status.OK).body(responseSample)

        assertFalse(responseComparator.compare(r1, r2))
    }

    @Test
    fun `Ensure that two responses are equal if there is an nonexistent property in ignored fields`() {
        val responseComparator = ResponseComparator(om, listOf("testNestedObj.nested"))

        val responseSampleForCompare = """
            {
                "id": "123456789",
                "testNestedObj": {
                    "nestedId": 123
                },
                "testArray": [
                    {
                        "id": 1,
                        "value": "test1"
                    },
                    {
                        "id": 2,
                        "value": "test2"
                    }
                ]
            }
        """
        val r2 = Response(Status.OK).body(responseSampleForCompare)
        val r1 = Response(Status.OK).body(responseSample)

        assertTrue(responseComparator.compare(r1, r2))
    }

}