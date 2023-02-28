package com.macro.mall.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssetOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AssetOrderExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNull() {
            addCriterion("order_num is null");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNotNull() {
            addCriterion("order_num is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNumEqualTo(String value) {
            addCriterion("order_num =", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotEqualTo(String value) {
            addCriterion("order_num <>", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThan(String value) {
            addCriterion("order_num >", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThanOrEqualTo(String value) {
            addCriterion("order_num >=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThan(String value) {
            addCriterion("order_num <", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThanOrEqualTo(String value) {
            addCriterion("order_num <=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLike(String value) {
            addCriterion("order_num like", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotLike(String value) {
            addCriterion("order_num not like", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumIn(List<String> values) {
            addCriterion("order_num in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotIn(List<String> values) {
            addCriterion("order_num not in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumBetween(String value1, String value2) {
            addCriterion("order_num between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotBetween(String value1, String value2) {
            addCriterion("order_num not between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(String value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(String value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(String value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(String value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(String value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(String value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLike(String value) {
            addCriterion("order_type like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotLike(String value) {
            addCriterion("order_type not like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<String> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<String> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(String value1, String value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(String value1, String value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andCzrIsNull() {
            addCriterion("czr is null");
            return (Criteria) this;
        }

        public Criteria andCzrIsNotNull() {
            addCriterion("czr is not null");
            return (Criteria) this;
        }

        public Criteria andCzrEqualTo(String value) {
            addCriterion("czr =", value, "czr");
            return (Criteria) this;
        }

        public Criteria andCzrNotEqualTo(String value) {
            addCriterion("czr <>", value, "czr");
            return (Criteria) this;
        }

        public Criteria andCzrGreaterThan(String value) {
            addCriterion("czr >", value, "czr");
            return (Criteria) this;
        }

        public Criteria andCzrGreaterThanOrEqualTo(String value) {
            addCriterion("czr >=", value, "czr");
            return (Criteria) this;
        }

        public Criteria andCzrLessThan(String value) {
            addCriterion("czr <", value, "czr");
            return (Criteria) this;
        }

        public Criteria andCzrLessThanOrEqualTo(String value) {
            addCriterion("czr <=", value, "czr");
            return (Criteria) this;
        }

        public Criteria andCzrLike(String value) {
            addCriterion("czr like", value, "czr");
            return (Criteria) this;
        }

        public Criteria andCzrNotLike(String value) {
            addCriterion("czr not like", value, "czr");
            return (Criteria) this;
        }

        public Criteria andCzrIn(List<String> values) {
            addCriterion("czr in", values, "czr");
            return (Criteria) this;
        }

        public Criteria andCzrNotIn(List<String> values) {
            addCriterion("czr not in", values, "czr");
            return (Criteria) this;
        }

        public Criteria andCzrBetween(String value1, String value2) {
            addCriterion("czr between", value1, value2, "czr");
            return (Criteria) this;
        }

        public Criteria andCzrNotBetween(String value1, String value2) {
            addCriterion("czr not between", value1, value2, "czr");
            return (Criteria) this;
        }

        public Criteria andCzrlxdhIsNull() {
            addCriterion("czrlxdh is null");
            return (Criteria) this;
        }

        public Criteria andCzrlxdhIsNotNull() {
            addCriterion("czrlxdh is not null");
            return (Criteria) this;
        }

        public Criteria andCzrlxdhEqualTo(String value) {
            addCriterion("czrlxdh =", value, "czrlxdh");
            return (Criteria) this;
        }

        public Criteria andCzrlxdhNotEqualTo(String value) {
            addCriterion("czrlxdh <>", value, "czrlxdh");
            return (Criteria) this;
        }

        public Criteria andCzrlxdhGreaterThan(String value) {
            addCriterion("czrlxdh >", value, "czrlxdh");
            return (Criteria) this;
        }

        public Criteria andCzrlxdhGreaterThanOrEqualTo(String value) {
            addCriterion("czrlxdh >=", value, "czrlxdh");
            return (Criteria) this;
        }

        public Criteria andCzrlxdhLessThan(String value) {
            addCriterion("czrlxdh <", value, "czrlxdh");
            return (Criteria) this;
        }

        public Criteria andCzrlxdhLessThanOrEqualTo(String value) {
            addCriterion("czrlxdh <=", value, "czrlxdh");
            return (Criteria) this;
        }

        public Criteria andCzrlxdhLike(String value) {
            addCriterion("czrlxdh like", value, "czrlxdh");
            return (Criteria) this;
        }

        public Criteria andCzrlxdhNotLike(String value) {
            addCriterion("czrlxdh not like", value, "czrlxdh");
            return (Criteria) this;
        }

        public Criteria andCzrlxdhIn(List<String> values) {
            addCriterion("czrlxdh in", values, "czrlxdh");
            return (Criteria) this;
        }

        public Criteria andCzrlxdhNotIn(List<String> values) {
            addCriterion("czrlxdh not in", values, "czrlxdh");
            return (Criteria) this;
        }

        public Criteria andCzrlxdhBetween(String value1, String value2) {
            addCriterion("czrlxdh between", value1, value2, "czrlxdh");
            return (Criteria) this;
        }

        public Criteria andCzrlxdhNotBetween(String value1, String value2) {
            addCriterion("czrlxdh not between", value1, value2, "czrlxdh");
            return (Criteria) this;
        }

        public Criteria andZlrIsNull() {
            addCriterion("zlr is null");
            return (Criteria) this;
        }

        public Criteria andZlrIsNotNull() {
            addCriterion("zlr is not null");
            return (Criteria) this;
        }

        public Criteria andZlrEqualTo(String value) {
            addCriterion("zlr =", value, "zlr");
            return (Criteria) this;
        }

        public Criteria andZlrNotEqualTo(String value) {
            addCriterion("zlr <>", value, "zlr");
            return (Criteria) this;
        }

        public Criteria andZlrGreaterThan(String value) {
            addCriterion("zlr >", value, "zlr");
            return (Criteria) this;
        }

        public Criteria andZlrGreaterThanOrEqualTo(String value) {
            addCriterion("zlr >=", value, "zlr");
            return (Criteria) this;
        }

        public Criteria andZlrLessThan(String value) {
            addCriterion("zlr <", value, "zlr");
            return (Criteria) this;
        }

        public Criteria andZlrLessThanOrEqualTo(String value) {
            addCriterion("zlr <=", value, "zlr");
            return (Criteria) this;
        }

        public Criteria andZlrLike(String value) {
            addCriterion("zlr like", value, "zlr");
            return (Criteria) this;
        }

        public Criteria andZlrNotLike(String value) {
            addCriterion("zlr not like", value, "zlr");
            return (Criteria) this;
        }

        public Criteria andZlrIn(List<String> values) {
            addCriterion("zlr in", values, "zlr");
            return (Criteria) this;
        }

        public Criteria andZlrNotIn(List<String> values) {
            addCriterion("zlr not in", values, "zlr");
            return (Criteria) this;
        }

        public Criteria andZlrBetween(String value1, String value2) {
            addCriterion("zlr between", value1, value2, "zlr");
            return (Criteria) this;
        }

        public Criteria andZlrNotBetween(String value1, String value2) {
            addCriterion("zlr not between", value1, value2, "zlr");
            return (Criteria) this;
        }

        public Criteria andZlrlxdhIsNull() {
            addCriterion("zlrlxdh is null");
            return (Criteria) this;
        }

        public Criteria andZlrlxdhIsNotNull() {
            addCriterion("zlrlxdh is not null");
            return (Criteria) this;
        }

        public Criteria andZlrlxdhEqualTo(String value) {
            addCriterion("zlrlxdh =", value, "zlrlxdh");
            return (Criteria) this;
        }

        public Criteria andZlrlxdhNotEqualTo(String value) {
            addCriterion("zlrlxdh <>", value, "zlrlxdh");
            return (Criteria) this;
        }

        public Criteria andZlrlxdhGreaterThan(String value) {
            addCriterion("zlrlxdh >", value, "zlrlxdh");
            return (Criteria) this;
        }

        public Criteria andZlrlxdhGreaterThanOrEqualTo(String value) {
            addCriterion("zlrlxdh >=", value, "zlrlxdh");
            return (Criteria) this;
        }

        public Criteria andZlrlxdhLessThan(String value) {
            addCriterion("zlrlxdh <", value, "zlrlxdh");
            return (Criteria) this;
        }

        public Criteria andZlrlxdhLessThanOrEqualTo(String value) {
            addCriterion("zlrlxdh <=", value, "zlrlxdh");
            return (Criteria) this;
        }

        public Criteria andZlrlxdhLike(String value) {
            addCriterion("zlrlxdh like", value, "zlrlxdh");
            return (Criteria) this;
        }

        public Criteria andZlrlxdhNotLike(String value) {
            addCriterion("zlrlxdh not like", value, "zlrlxdh");
            return (Criteria) this;
        }

        public Criteria andZlrlxdhIn(List<String> values) {
            addCriterion("zlrlxdh in", values, "zlrlxdh");
            return (Criteria) this;
        }

        public Criteria andZlrlxdhNotIn(List<String> values) {
            addCriterion("zlrlxdh not in", values, "zlrlxdh");
            return (Criteria) this;
        }

        public Criteria andZlrlxdhBetween(String value1, String value2) {
            addCriterion("zlrlxdh between", value1, value2, "zlrlxdh");
            return (Criteria) this;
        }

        public Criteria andZlrlxdhNotBetween(String value1, String value2) {
            addCriterion("zlrlxdh not between", value1, value2, "zlrlxdh");
            return (Criteria) this;
        }

        public Criteria andZfsjIsNull() {
            addCriterion("zfsj is null");
            return (Criteria) this;
        }

        public Criteria andZfsjIsNotNull() {
            addCriterion("zfsj is not null");
            return (Criteria) this;
        }

        public Criteria andZfsjEqualTo(Date value) {
            addCriterion("zfsj =", value, "zfsj");
            return (Criteria) this;
        }

        public Criteria andZfsjNotEqualTo(Date value) {
            addCriterion("zfsj <>", value, "zfsj");
            return (Criteria) this;
        }

        public Criteria andZfsjGreaterThan(Date value) {
            addCriterion("zfsj >", value, "zfsj");
            return (Criteria) this;
        }
        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createtime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andZfsjGreaterThanOrEqualTo(Date value) {
            addCriterion("zfsj >=", value, "zfsj");
            return (Criteria) this;
        }
        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createtime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andZfsjLessThan(Date value) {
            addCriterion("zfsj <", value, "zfsj");
            return (Criteria) this;
        }
        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createtime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andZfsjLessThanOrEqualTo(Date value) {
            addCriterion("zfsj <=", value, "zfsj");
            return (Criteria) this;
        }
        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createtime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andZfsjIn(List<Date> values) {
            addCriterion("zfsj in", values, "zfsj");
            return (Criteria) this;
        }

        public Criteria andZfsjNotIn(List<Date> values) {
            addCriterion("zfsj not in", values, "zfsj");
            return (Criteria) this;
        }

        public Criteria andZfsjBetween(Date value1, Date value2) {
            addCriterion("zfsj between", value1, value2, "zfsj");
            return (Criteria) this;
        }
        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createtime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andZfsjNotBetween(Date value1, Date value2) {
            addCriterion("zfsj not between", value1, value2, "zfsj");
            return (Criteria) this;
        }

        public Criteria andZffsIsNull() {
            addCriterion("zffs is null");
            return (Criteria) this;
        }

        public Criteria andZffsIsNotNull() {
            addCriterion("zffs is not null");
            return (Criteria) this;
        }

        public Criteria andZffsEqualTo(String value) {
            addCriterion("zffs =", value, "zffs");
            return (Criteria) this;
        }

        public Criteria andZffsNotEqualTo(String value) {
            addCriterion("zffs <>", value, "zffs");
            return (Criteria) this;
        }

        public Criteria andZffsGreaterThan(String value) {
            addCriterion("zffs >", value, "zffs");
            return (Criteria) this;
        }

        public Criteria andZffsGreaterThanOrEqualTo(String value) {
            addCriterion("zffs >=", value, "zffs");
            return (Criteria) this;
        }

        public Criteria andZffsLessThan(String value) {
            addCriterion("zffs <", value, "zffs");
            return (Criteria) this;
        }

        public Criteria andZffsLessThanOrEqualTo(String value) {
            addCriterion("zffs <=", value, "zffs");
            return (Criteria) this;
        }

        public Criteria andZffsLike(String value) {
            addCriterion("zffs like", value, "zffs");
            return (Criteria) this;
        }

        public Criteria andZffsNotLike(String value) {
            addCriterion("zffs not like", value, "zffs");
            return (Criteria) this;
        }

        public Criteria andZffsIn(List<String> values) {
            addCriterion("zffs in", values, "zffs");
            return (Criteria) this;
        }

        public Criteria andZffsNotIn(List<String> values) {
            addCriterion("zffs not in", values, "zffs");
            return (Criteria) this;
        }

        public Criteria andZffsBetween(String value1, String value2) {
            addCriterion("zffs between", value1, value2, "zffs");
            return (Criteria) this;
        }

        public Criteria andZffsNotBetween(String value1, String value2) {
            addCriterion("zffs not between", value1, value2, "zffs");
            return (Criteria) this;
        }

        public Criteria andZjeIsNull() {
            addCriterion("zje is null");
            return (Criteria) this;
        }

        public Criteria andZjeIsNotNull() {
            addCriterion("zje is not null");
            return (Criteria) this;
        }

        public Criteria andZjeEqualTo(Long value) {
            addCriterion("zje =", value, "zje");
            return (Criteria) this;
        }

        public Criteria andZjeNotEqualTo(Long value) {
            addCriterion("zje <>", value, "zje");
            return (Criteria) this;
        }

        public Criteria andZjeGreaterThan(Long value) {
            addCriterion("zje >", value, "zje");
            return (Criteria) this;
        }

        public Criteria andZjeGreaterThanOrEqualTo(Long value) {
            addCriterion("zje >=", value, "zje");
            return (Criteria) this;
        }

        public Criteria andZjeLessThan(Long value) {
            addCriterion("zje <", value, "zje");
            return (Criteria) this;
        }

        public Criteria andZjeLessThanOrEqualTo(Long value) {
            addCriterion("zje <=", value, "zje");
            return (Criteria) this;
        }

        public Criteria andZjeIn(List<Long> values) {
            addCriterion("zje in", values, "zje");
            return (Criteria) this;
        }

        public Criteria andZjeNotIn(List<Long> values) {
            addCriterion("zje not in", values, "zje");
            return (Criteria) this;
        }

        public Criteria andZjeBetween(Long value1, Long value2) {
            addCriterion("zje between", value1, value2, "zje");
            return (Criteria) this;
        }

        public Criteria andZjeNotBetween(Long value1, Long value2) {
            addCriterion("zje not between", value1, value2, "zje");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}