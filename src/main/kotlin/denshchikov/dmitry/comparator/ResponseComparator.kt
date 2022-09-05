package denshchikov.dmitry.comparator

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.http4k.core.Response

class ResponseComparator(objectMapper: ObjectMapper, fieldsToExclude: Collection<String> = listOf()) {

    private val om = objectMapper
    private val fieldsToIgnore = fieldsToExclude;

    fun compare(o1: Response, o2: Response): Boolean {
        val tree1: JsonNode = om.readTree(o1.bodyString())
        val tree2: JsonNode = om.readTree(o2.bodyString())

        if (tree1.nodeType != (tree2.nodeType)) {
            return false
        }

        if (tree1 is ObjectNode && tree2 is ObjectNode) {
            for (fieldToIgnore in fieldsToIgnore) {
                if (fieldToIgnore.contains(".")) {
                    val paths = fieldToIgnore.split(".")

                    var tree1Cursor: JsonNode = tree1
                    var tree2Cursor: JsonNode = tree2

                    for (i in 0 until paths.size - 1) {
                        tree1Cursor = tree1Cursor.get(paths[i])
                        tree2Cursor = tree2Cursor.get(paths[i])
                    }

                    (tree1Cursor as ObjectNode).remove(paths.last())
                    (tree2Cursor as ObjectNode).remove(paths.last())
                } else {
                    tree1.remove(fieldToIgnore)
                    tree2.remove(fieldToIgnore)
                }
            }

        }

        return tree1 == tree2
    }

}