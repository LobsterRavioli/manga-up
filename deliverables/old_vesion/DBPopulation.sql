USE MANGA_UP;

LOAD DATA LOCAL INFILE 'dataProduct.sql' INTO TABLE product IGNORE 2 LINES(prd_name,prd_brand,prd_price,prd_weight,prd_height,prd_lenght,prd_state,prd_description,prd_type_of_product,prd_quantity,prd_image);