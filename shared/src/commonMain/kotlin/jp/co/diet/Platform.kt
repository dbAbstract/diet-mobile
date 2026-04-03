package jp.co.diet

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform