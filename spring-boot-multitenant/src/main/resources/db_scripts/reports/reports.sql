-- purchase sale monthly report
create or replace view inventory.purchasesalemonthlyreport

AS

select tenantid, month, year, sum(purchaseamt) as purchaseamt, sum(saleamt) as saleamt, sum(profit) as profit,  ROW_NUMBER () OVER (ORDER BY tenantid) as id

from (

	select
	po.tenantid as tenantid,
	date_part('month', po.confirmdate) as month,
	date_part('year', po.confirmdate) as year,
	(case when po.type = 'purchase' then  po.totalamount end) as purchaseamt,
	(case when po.type = 'sale' then po.totalamount end) as saleamt,
	(case when po.type = 'sale' then po.profit end) as profit

	from purchaseorder po
	order by po.confirmdate
) as detail

group by tenantid, month, year
order by tenantid, month, year



-- Product purchase sales

create or replace view inventory.productmonthlyreport

AS

select tenantid, prodid, month, year, sum(purchaseqty) as purchaseqty, sum(purchaseamt) as purchaseamt,
sum(saleqty) as saleqty, sum(saleamt) as saleamt, sum(profit) as profit, ROW_NUMBER () OVER (ORDER BY tenantid) as id

from (

	select
	p.tenantid as tenantid,
	p.id as prodid,
	date_part('month', po.confirmdate) as month,
	date_part('year', po.confirmdate) as year,
	(case when po.type = 'sale' then pop.quantity end) as saleqty ,
	(case when po.type = 'sale' then pop.price * pop.quantity end) as saleamt,
	(case when po.type = 'sale' then pop.profit end) as profit,
	(case when po.type = 'purchase' then pop.quantity end) as purchaseqty ,
	(case when po.type = 'purchase' then  pop.price * pop.quantity end) as purchaseamt
	from poproduct pop, purchaseorder po, product p
	where pop.productid = p.id
	and pop.poid = po.id
	order by po.confirmdate
) as detail

group by tenantid, prodid, month, year
order by tenantid, prodid, month, year



--Supplier Monthly report

create or replace view inventory.suppliermonthlyreport
AS
select tenantid, cid, month, year, sum(purchaseamt) as purchaseamt,  ROW_NUMBER () OVER (ORDER BY tenantid) as id

from (

	select
	po.tenantid as tenantid,
	c.id as cid,
	date_part('month', po.confirmdate) as month,
	date_part('year', po.confirmdate) as year,
	po.totalamount as purchaseamt

	from purchaseorder po, company c

	where po.type = 'purchase'
	and c.type = 'supplier'
	and po.supplierid = c.id
	order by po.confirmdate


) as detail

group by tenantid, cid, month, year
order by tenantid, cid, month, year

--customer report
create or replace view inventory.customermonthlyreport

AS

select  tenantid,  cid, month, year, sum(totamt) as saleamt,sum(profit) as profit, ROW_NUMBER () OVER (ORDER BY tenantid) as id

from (
	select
	po.tenantid as tenantid,
	c.id as cid,
	date_part('month', po.confirmdate) as month,
	date_part('year', po.confirmdate) as year,
	po.totalamount as totamt,
	po.profit as profit
	from purchaseorder po, company c

	where po.type = 'sale'
	and c.type = 'customer'
	and po.customerid = c.id
	order by po.confirmdate

) as detail

group by tenantid, cid, month, year
order by tenantid, cid, month, year


--product supplier monthly report
create or replace view inventory.prodsupmonthlyreport
AS
select tenantid, prodid, prodname, cid, name, month, year, sum(purchaseqty) as purchaseqty, sum(purchaseamt) as purchaseamt,
ROW_NUMBER () OVER (ORDER BY tenantid) as id

from (

	select
	p.tenantid as tenantid,
	p.id as prodid,
	p.name as prodname,
	c.id as cid,
	c.name as name,
	date_part('month', po.confirmdate) as month,
	date_part('year', po.confirmdate) as year,
	(case when po.type = 'purchase' then pop.quantity end) as purchaseqty ,
	(case when po.type = 'purchase' then  pop.price * pop.quantity end) as purchaseamt
	from poproduct pop, purchaseorder po, product p, company c

	where po.type = 'purchase'
	and c.type = 'supplier'
	and pop.productid = p.id
	and pop.poid = po.id
	and po.supplierid = c.id
	order by po.confirmdate

) as detail

group by tenantid, prodid, prodname, cid, name, month, year
order by tenantid, prodid, cid, month, year

--product customer monthly report
create or replace view inventory.prodcustmonthlyreport
AS
select tenantid, prodid, prodname, cid, name, month, year, sum(saleqty) as saleqty, sum(saleamt) as saleamt, sum(profit) as profit,
ROW_NUMBER () OVER (ORDER BY tenantid) as id

from (

	select
	p.tenantid as tenantid,
	p.id as prodid,
	p.name as prodname,
	c.id as cid,
	c.name as name,
	date_part('month', po.confirmdate) as month,
	date_part('year', po.confirmdate) as year,
	(case when po.type = 'sale' then pop.quantity end) as saleqty,
	(case when po.type = 'sale' then pop.price * pop.quantity end) as saleamt,
	(case when po.type = 'sale' then pop.profit end) as profit
	from poproduct pop, purchaseorder po, product p, company c

	where po.type = 'sale'
	and c.type = 'customer'
	and pop.productid = p.id
	and pop.poid = po.id
	and po.customerid = c.id
	order by po.confirmdate

) as detail

group by tenantid, prodid, prodname, cid, name, month, year
order by tenantid, prodid, cid, month, year
--


