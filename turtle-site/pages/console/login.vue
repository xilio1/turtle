<script setup lang="ts">
import type {FormError, FormSubmitEvent} from '@nuxt/ui'
import {Https} from "~/composables/https";
import {API} from "~/composables/api";

const state = reactive({
  username: '',
  password: ''
})
const validate = (state: any): FormError[] => {
  const errors = []
  if (!state.username) errors.push({name: 'username', message: '用户名不能为空'})
  if (!state.password) errors.push({name: 'password', message: '请输入密码'})
  return errors
}

async function onSubmit(event: FormSubmitEvent<typeof state>) {
  Https.action(API.USER.login, {
    body: state,
  }).then((res: any) => {
    if (res.code == 200) {
      const tokenTimeout = res.data.tokenTimeout
      const expirationDate = new Date(Date.now() + tokenTimeout * 1000);
      const cookie = useCookie('Authorization', {
        expires: expirationDate,
        maxAge: tokenTimeout, // 有效期
        path: '/', // 确保在整个站点可用
        sameSite: 'strict',//防止 CSRF 攻击 严格限制跨站发送 Cookie，增强安全性。
        //secure: true, // 仅在 HTTPS 下传输
      })
      cookie.value = res.data.tokenValue
      useRouter().push('/console/')
    }
  })
}

const {seo} = useSiteConfig().value
</script>
<template>
  <div class="min-h-screen flex flex-col items-center justify-center   px-4 ">
    <div class="text-[25px] pb-4">{{ seo.site_title }} 后台登陆</div>
    <UForm
        :validate="validate"
        :state="state"
        class="w-full flex flex-col max-w-md space-y-4 gap-5"
        @submit="onSubmit">
      <UFormField name="username">
        <UInput size="xl" variant="soft" v-model="state.username" class="w-full" placeholder="用户名/邮箱"/>
      </UFormField>

      <UFormField name="password">
        <UInput size="xl" variant="soft" v-model="state.password" type="password" class="w-full" placeholder="密码"/>
      </UFormField>
      <UButton size="xl" type="submit" class="w-full flex justify-center text-center">
        登陆
      </UButton>
    </UForm>
  </div>
</template>
