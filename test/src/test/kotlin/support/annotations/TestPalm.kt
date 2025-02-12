package support.annotations

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class TestPalm(vararg val testCaseId: Int)
