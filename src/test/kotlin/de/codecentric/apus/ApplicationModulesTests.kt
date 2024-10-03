package de.codecentric.apus

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter
import org.springframework.modulith.test.ApplicationModuleTest
import kotlin.test.Test

@SpringBootTest
@ApplicationModuleTest
class ApplicationModulesTests {

    @Test
    fun contextLoads() {
        ApplicationModules.of(ApusApplication::class.java).verify().let { modules ->
            modules.map { module-> module.name }.forEach { name -> println("Found module $name") }
            Documenter(modules).writeModulesAsPlantUml().writeDocumentation()
        }
    }
}