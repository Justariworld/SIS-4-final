package com.example.sis4final

class PostRepository(
    private val api: ApiService,
    private val postDao: PostDao
) {

    /**
     * Оффлайн-поведение:
     * 1) Пробуем загрузить из сети.
     * 2) Если ок — сохраняем в Room и возвращаем сетевые данные.
     * 3) Если ошибка — берём кэш из Room.
     *    Если кэша нет — кидаем ошибку дальше.
     */
    suspend fun loadPosts(): List<Post> {
        return try {
            val postsFromNetwork = api.getPosts()

            postDao.clearPosts()
            postDao.insertPosts(postsFromNetwork)

            postsFromNetwork
        } catch (e: Exception) {
            val cached = postDao.getAllPosts()
            if (cached.isNotEmpty()){
                cached
            }
            else {
                throw e
            }
        }
    }
}
