


-- Account balance
create or replace view inventory.accountbalance

AS

select tenantid, mycompanyid, acctid, sum(dramt) as totdramt, sum(cramt) as totcramt,
ROW_NUMBER () OVER (ORDER BY tenantid) as id

from (
	select tenantid, mycompanyid, acctid,
	(case when j.drcrtype = 1 then j.amount end) as dramt ,
	(case when j.drcrtype = 2 then j.amount end) as cramt

	from inventory.journal j
	) as detail

	group by tenantid, mycompanyid, acctid
	order by tenantid, mycompanyid, acctid;



	-- Available Stock

	create or replace view inventory.completedordersview

    AS

    select tenantid, productid, sum(purqty) as totpurqty, sum(saleqty) as totsaleqty,
    ROW_NUMBER () OVER (ORDER BY tenantid) as id

    from (
    	select invp.tenantid as tenantid, productid,
    	(case when inv.type = 1 then invp.quantity end) as purqty ,
    	(case when inv.type = 2 then invp.quantity end) as saleqty

    	from invproduct invp, poproduct pop, invoice inv
    	where inv.status = 2
    	and invp.invid = inv.id
    	and invp.popid =  pop.id

    ) as detail

    	group by tenantid, productid
    	order by tenantid, productid;