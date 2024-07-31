FROM golang:alpine AS builder
RUN apk --no-cache add git ca-certificates

RUN mkdir /build
ADD . /build/
WORKDIR /build/cmd
RUN CGO_ENABLED=0 GOOS=linux go build -ldflags '-s -w -extldflags "-static"' -o /build/main .

FROM scratch
COPY --from=builder /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/
COPY --from=builder /build/.env /app/.env
COPY --from=builder /build/main /app/cmd/
WORKDIR /app/cmd
CMD ["./main"]