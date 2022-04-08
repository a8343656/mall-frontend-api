# mall-frontend-api  
#### 專案結構介紹  

-- config (跨域設定與攔截器設定檔)  
-- controller (服務統一入口)  
-- dto (各個 controleer 所用的 dto)  
-- entity (對應 DB 中的表)  
-- handler (用於 exception 的回應，目前暫時只用於 spring validate 的 exception 處理)  
-- repository (下SQL)  
-- service (邏輯層)  
-- util  

#### DB 結構圖  
![image](https://raw.githubusercontent.com/a8343656/mall-frontend-api/7a8a9993126220283c5ae9270bd5f3d1cdff53d6/DB_ER.jpg)
