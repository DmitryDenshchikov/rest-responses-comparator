package denshchikov.dmitry.domain

enum class CliArgument(val cliName: String) {

    URLS("urls"),
    REQUEST_TYPE("request-type"),
    REQUEST_BODY("request-body"),
    FIELDS_TO_EXCLUDE("fields-to-exclude");

    companion object {
        fun getEnumValue(value: String): CliArgument {
            for (cliArgument in CliArgument.values()) {
                if (cliArgument.cliName == value) {
                    return cliArgument
                }
            }
            throw IllegalArgumentException("No enum value was found for $value")
        }
    }

}