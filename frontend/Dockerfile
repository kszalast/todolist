FROM node:20-alpine AS builder

WORKDIR /app

COPY .npmrc /root/.npmrc

COPY package.json package-lock.json ./

RUN npm install


COPY . .

RUN npm run build

FROM node:20-alpine
WORKDIR /app

COPY --from=builder /app/package.json ./
COPY --from=builder /app/package-lock.json ./
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/.next ./.next
COPY --from=builder /app/public ./public

CMD ["npm", "run", "start"]
EXPOSE 3000
