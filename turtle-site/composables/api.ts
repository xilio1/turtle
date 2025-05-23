/**
 * 后台管理接口定义
 */
export const API = {
    USER: {
        login: {url: '/user/login', method: 'POST' as const},
        logout: {url: '/user/logout', method: 'POST' as const},
        updateProfile: {url: '/user/update-profile', method: 'PUT' as const},
    },
    ARTICLE: {
        list: {url: '/article/list', method: 'POST' as const},
        get: {url: '/article/get', method: 'GET' as const},
        delete: {url: '/article/delete', method: 'DELETE' as const},
        save: {url: '/article/save', method: 'POST' as const},
    },
    FILE: {
        upload_image: {url: '/file/upload-image', method: 'POST' as const},

    },
    CONFIG: {
        get: {url: '/config/get', method: 'GET' as const},
        save: {url: '/config/save', method: 'POST' as const},
    },
    menu: {
        list: {url: '/menu/list', method: 'GET' as const},
    }
} as const;
