/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2016/2/27 ĞÇÆÚÁù 18:47:10                       */
/*==============================================================*/


if exists (select 1
            from  sysindexes
           where  id    = object_id('budget')
            and   name  = 'have_FK'
            and   indid > 0
            and   indid < 255)
   drop index budget.have_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('budget')
            and   type = 'U')
   drop table budget
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('detail')
            and   name  = 'payduiying_FK'
            and   indid > 0
            and   indid < 255)
   drop index detail.payduiying_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('detail')
            and   name  = 'incomeduiying_FK'
            and   indid > 0
            and   indid < 255)
   drop index detail.incomeduiying_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('detail')
            and   name  = 'own_FK'
            and   indid > 0
            and   indid < 255)
   drop index detail.own_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('detail')
            and   type = 'U')
   drop table detail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('incometype')
            and   type = 'U')
   drop table incometype
go

if exists (select 1
            from  sysobjects
           where  id = object_id('member')
            and   type = 'U')
   drop table member
go

if exists (select 1
            from  sysobjects
           where  id = object_id('paytype')
            and   type = 'U')
   drop table paytype
go

/*==============================================================*/
/* Table: budget                                                */
/*==============================================================*/
create table budget (
   budgetid             int                  not null,
   memberid             int                  null,
   budgetmoney          money                null,
   budgettimepart       datetime             null,
   budgetszclassify     bit                  null,
   incometotal          money                null,
   paytotal             money                null,
   constraint PK_BUDGET primary key nonclustered (budgetid)
)
go

/*==============================================================*/
/* Index: have_FK                                               */
/*==============================================================*/
create index have_FK on budget (
memberid ASC
)
go

/*==============================================================*/
/* Table: detail                                                */
/*==============================================================*/
create table detail (
   detailid             int                  not null,
   memberid             int                  null,
   paytypeid            int                  null,
   incometypeid         int                  null,
   timepoint            datetime             null,
   adress               varchar(20)          null,
   detailszclassify     bit                  null,
   detailmoney          money                null,
   detailmark           varchar(20)          null,
   constraint PK_DETAIL primary key nonclustered (detailid)
)
go

/*==============================================================*/
/* Index: own_FK                                                */
/*==============================================================*/
create index own_FK on detail (
memberid ASC
)
go

/*==============================================================*/
/* Index: incomeduiying_FK                                      */
/*==============================================================*/
create index incomeduiying_FK on detail (
incometypeid ASC
)
go

/*==============================================================*/
/* Index: payduiying_FK                                         */
/*==============================================================*/
create index payduiying_FK on detail (
paytypeid ASC
)
go

/*==============================================================*/
/* Table: incometype                                            */
/*==============================================================*/
create table incometype (
   incometypeid         int                  not null,
   incometypename       varchar(20)          null,
   constraint PK_INCOMETYPE primary key nonclustered (incometypeid)
)
go

/*==============================================================*/
/* Table: member                                                */
/*==============================================================*/
create table member (
   memberid             int                  not null,
   membertype           varchar(20)          null,
   membername           varchar(20)          null,
   membertele           varchar(20)          null,
   constraint PK_MEMBER primary key nonclustered (memberid)
)
go

/*==============================================================*/
/* Table: paytype                                               */
/*==============================================================*/
create table paytype (
   paytypeid            int                  not null,
   paytypeclassify      varchar(20)          null,
   paytypename          varchar(20)          null,
   constraint PK_PAYTYPE primary key nonclustered (paytypeid)
)
go

