package denshchikov.dmitry.domain

enum class CliArgument(val cliName: String) {

    URLS("urls"),
    REQUEST_TYPE("request-type"),
    REQUEST_BODY("request-type");

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