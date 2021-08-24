import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.file.Paths

/**
 * myserver073
 * Class: WebConfig
 * Created by mac on 2021/08/10.
 *
 * Description:
 */
@Configuration
class WebConfig: WebMvcConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        exposeDirectory("user-phtos", registry)
    }

    fun exposeDirectory(dirName: String, registry: ResourceHandlerRegistry) {
        val uploadDir = Paths.get(dirName)
        val uploadPath = uploadDir.toFile().absolutePath

        registry.addResourceHandler("/" + dirName + "/**")
            .addResourceLocations("file:/" + "/" )
    }
}