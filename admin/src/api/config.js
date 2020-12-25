import axios from 'axios'


/**
 * axios 处理并发请求的助手函数
 */
axios.defaults.baseURL = '/api'

axios.interceptors.request.use(
    config => {
        return config
    },
    function (error) {
        return Promise.reject(error)
    }
)
axios.interceptors.response.use(function (response) {
    //可以写判断获得的数据返回码
    return response
}, function (error) {
    return Promise.reject(error)
})
// 请求超时时间
axios.defaults.timeout = 60000

export default axios