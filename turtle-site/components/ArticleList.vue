<!-- components/ArticleList.vue -->
<template>
  <div class="w-full">
    <div
        v-for="article in list"
        :key="article.id"
        class="flex flex-col gap-4 pt-2">

      <NuxtLink :to="getDetailUrl(article)" class="mh-30">
        <div class="flex flex-row gap-1 items-center min-w-0">
          <UBadge v-if="true" size="sm" color="neutral" class="flex-shrink-0">原创</UBadge>
          <UBadge v-else size="sm" color="warning" class="flex-shrink-0">转载</UBadge>
          <p v-html="article.title" class="truncate font-semibold text-xl text-gray-900 ml-2"/>
        </div>
        <p v-html="article.description" class="text-[#8A919F] text-[16px] line-clamp-2 break-words"/>

        <div class="article-meta flex flex-row gap-4 items-center">
          <div class="text-[#8A919F] text-[15px] truncate">{{ formatDateTime(article.publishedAt) }}</div>
          <div class="tags flex flex-row gap-2">
            <NuxtLink
                v-for="tagName in article.tags"
                :key="tagName"
                :to="`/tag/${tagName}`">
              <UBadge
                  class="truncate"
                  size="sm"
                  color="neutral"
                  variant="soft">
                {{ tagName }}
              </UBadge>
            </NuxtLink>
          </div>
        </div>
      </NuxtLink>
      <USeparator color="neutral" type="dotted"/>
    </div>
  </div>
</template>

<script setup lang="ts">

import {formatDateTime} from "~/composables/Common";

defineProps({
  list: {
    type: Array,
    required: true,
    default: () => []
  },
})
const getDetailUrl = ((article: any) => {
  const base = `/detail/${article.id}`;
  if (article.isProtected) {
    return `${base}?p=1`
  }
  return base;
})

</script>
