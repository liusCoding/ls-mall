http://120.79.185.188/pay/create?orderId=1582317949140&amount=0.01&payType=WXPAY_NATIVE
--->
http://120.79.185.188：8081/pay/create?orderId=1582317949140&amount=0.01&payType=WXPAY_NATIVE

反向代理

location /pay/ {
    
    proxy_pass http://127.0.0.1:8080/pay;
}


