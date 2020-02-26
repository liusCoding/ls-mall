 http://120.79.185.188/api/products
 
 
 ->>反向代理
 
 http://120.79.185.188:8081/products
 
 nginx 配置文件
 
 location /api/ {
    proxy_pass http://127.0.0.1:8081/
 }