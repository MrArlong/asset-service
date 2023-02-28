package com.macro.mall.model;

import java.util.ArrayList;
import java.util.List;

public class AssetRoomExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AssetRoomExample() {
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

        public Criteria andFloorIdIsNull() {
            addCriterion("floor_id is null");
            return (Criteria) this;
        }

        public Criteria andFloorIdIsNotNull() {
            addCriterion("floor_id is not null");
            return (Criteria) this;
        }

        public Criteria andFloorIdEqualTo(Long value) {
            addCriterion("floor_id =", value, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdNotEqualTo(Long value) {
            addCriterion("floor_id <>", value, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdGreaterThan(Long value) {
            addCriterion("floor_id >", value, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdGreaterThanOrEqualTo(Long value) {
            addCriterion("floor_id >=", value, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdLessThan(Long value) {
            addCriterion("floor_id <", value, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdLessThanOrEqualTo(Long value) {
            addCriterion("floor_id <=", value, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdIn(List<Long> values) {
            addCriterion("floor_id in", values, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdNotIn(List<Long> values) {
            addCriterion("floor_id not in", values, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdBetween(Long value1, Long value2) {
            addCriterion("floor_id between", value1, value2, "floorId");
            return (Criteria) this;
        }

        public Criteria andFloorIdNotBetween(Long value1, Long value2) {
            addCriterion("floor_id not between", value1, value2, "floorId");
            return (Criteria) this;
        }

        public Criteria andRoomNumIsNull() {
            addCriterion("room_num is null");
            return (Criteria) this;
        }

        public Criteria andRoomNumIsNotNull() {
            addCriterion("room_num is not null");
            return (Criteria) this;
        }

        public Criteria andRoomNumEqualTo(String value) {
            addCriterion("room_num =", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumNotEqualTo(String value) {
            addCriterion("room_num <>", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumGreaterThan(String value) {
            addCriterion("room_num >", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumGreaterThanOrEqualTo(String value) {
            addCriterion("room_num >=", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumLessThan(String value) {
            addCriterion("room_num <", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumLessThanOrEqualTo(String value) {
            addCriterion("room_num <=", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumLike(String value) {
            addCriterion("room_num like", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumNotLike(String value) {
            addCriterion("room_num not like", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumIn(List<String> values) {
            addCriterion("room_num in", values, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumNotIn(List<String> values) {
            addCriterion("room_num not in", values, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumBetween(String value1, String value2) {
            addCriterion("room_num between", value1, value2, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumNotBetween(String value1, String value2) {
            addCriterion("room_num not between", value1, value2, "roomNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumIsNull() {
            addCriterion("floor_num is null");
            return (Criteria) this;
        }

        public Criteria andFloorNumIsNotNull() {
            addCriterion("floor_num is not null");
            return (Criteria) this;
        }

        public Criteria andFloorNumEqualTo(String value) {
            addCriterion("floor_num =", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumNotEqualTo(String value) {
            addCriterion("floor_num <>", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumGreaterThan(String value) {
            addCriterion("floor_num >", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumGreaterThanOrEqualTo(String value) {
            addCriterion("floor_num >=", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumLessThan(String value) {
            addCriterion("floor_num <", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumLessThanOrEqualTo(String value) {
            addCriterion("floor_num <=", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumLike(String value) {
            addCriterion("floor_num like", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumNotLike(String value) {
            addCriterion("floor_num not like", value, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumIn(List<String> values) {
            addCriterion("floor_num in", values, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumNotIn(List<String> values) {
            addCriterion("floor_num not in", values, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumBetween(String value1, String value2) {
            addCriterion("floor_num between", value1, value2, "floorNum");
            return (Criteria) this;
        }

        public Criteria andFloorNumNotBetween(String value1, String value2) {
            addCriterion("floor_num not between", value1, value2, "floorNum");
            return (Criteria) this;
        }

        public Criteria andAcreageIsNull() {
            addCriterion("acreage is null");
            return (Criteria) this;
        }

        public Criteria andAcreageIsNotNull() {
            addCriterion("acreage is not null");
            return (Criteria) this;
        }

        public Criteria andAcreageEqualTo(String value) {
            addCriterion("acreage =", value, "acreage");
            return (Criteria) this;
        }

        public Criteria andAcreageNotEqualTo(String value) {
            addCriterion("acreage <>", value, "acreage");
            return (Criteria) this;
        }

        public Criteria andAcreageGreaterThan(String value) {
            addCriterion("acreage >", value, "acreage");
            return (Criteria) this;
        }

        public Criteria andAcreageGreaterThanOrEqualTo(String value) {
            addCriterion("acreage >=", value, "acreage");
            return (Criteria) this;
        }

        public Criteria andAcreageLessThan(String value) {
            addCriterion("acreage <", value, "acreage");
            return (Criteria) this;
        }

        public Criteria andAcreageLessThanOrEqualTo(String value) {
            addCriterion("acreage <=", value, "acreage");
            return (Criteria) this;
        }

        public Criteria andAcreageLike(String value) {
            addCriterion("acreage like", value, "acreage");
            return (Criteria) this;
        }

        public Criteria andAcreageNotLike(String value) {
            addCriterion("acreage not like", value, "acreage");
            return (Criteria) this;
        }

        public Criteria andAcreageIn(List<String> values) {
            addCriterion("acreage in", values, "acreage");
            return (Criteria) this;
        }

        public Criteria andAcreageNotIn(List<String> values) {
            addCriterion("acreage not in", values, "acreage");
            return (Criteria) this;
        }

        public Criteria andAcreageBetween(String value1, String value2) {
            addCriterion("acreage between", value1, value2, "acreage");
            return (Criteria) this;
        }

        public Criteria andAcreageNotBetween(String value1, String value2) {
            addCriterion("acreage not between", value1, value2, "acreage");
            return (Criteria) this;
        }

        public Criteria andDecorationTypeIsNull() {
            addCriterion("decoration_type is null");
            return (Criteria) this;
        }

        public Criteria andDecorationTypeIsNotNull() {
            addCriterion("decoration_type is not null");
            return (Criteria) this;
        }

        public Criteria andDecorationTypeEqualTo(String value) {
            addCriterion("decoration_type =", value, "decorationType");
            return (Criteria) this;
        }

        public Criteria andDecorationTypeNotEqualTo(String value) {
            addCriterion("decoration_type <>", value, "decorationType");
            return (Criteria) this;
        }

        public Criteria andDecorationTypeGreaterThan(String value) {
            addCriterion("decoration_type >", value, "decorationType");
            return (Criteria) this;
        }

        public Criteria andDecorationTypeGreaterThanOrEqualTo(String value) {
            addCriterion("decoration_type >=", value, "decorationType");
            return (Criteria) this;
        }

        public Criteria andDecorationTypeLessThan(String value) {
            addCriterion("decoration_type <", value, "decorationType");
            return (Criteria) this;
        }

        public Criteria andDecorationTypeLessThanOrEqualTo(String value) {
            addCriterion("decoration_type <=", value, "decorationType");
            return (Criteria) this;
        }

        public Criteria andDecorationTypeLike(String value) {
            addCriterion("decoration_type like", value, "decorationType");
            return (Criteria) this;
        }

        public Criteria andDecorationTypeNotLike(String value) {
            addCriterion("decoration_type not like", value, "decorationType");
            return (Criteria) this;
        }

        public Criteria andDecorationTypeIn(List<String> values) {
            addCriterion("decoration_type in", values, "decorationType");
            return (Criteria) this;
        }

        public Criteria andDecorationTypeNotIn(List<String> values) {
            addCriterion("decoration_type not in", values, "decorationType");
            return (Criteria) this;
        }

        public Criteria andDecorationTypeBetween(String value1, String value2) {
            addCriterion("decoration_type between", value1, value2, "decorationType");
            return (Criteria) this;
        }

        public Criteria andDecorationTypeNotBetween(String value1, String value2) {
            addCriterion("decoration_type not between", value1, value2, "decorationType");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(String value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(String value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(String value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(String value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(String value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(String value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLike(String value) {
            addCriterion("price like", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotLike(String value) {
            addCriterion("price not like", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<String> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<String> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(String value1, String value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(String value1, String value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andIsOccupancyIsNull() {
            addCriterion("is_occupancy is null");
            return (Criteria) this;
        }

        public Criteria andIsOccupancyIsNotNull() {
            addCriterion("is_occupancy is not null");
            return (Criteria) this;
        }

        public Criteria andIsOccupancyEqualTo(String value) {
            addCriterion("is_occupancy =", value, "isOccupancy");
            return (Criteria) this;
        }

        public Criteria andIsOccupancyNotEqualTo(String value) {
            addCriterion("is_occupancy <>", value, "isOccupancy");
            return (Criteria) this;
        }

        public Criteria andIsOccupancyGreaterThan(String value) {
            addCriterion("is_occupancy >", value, "isOccupancy");
            return (Criteria) this;
        }

        public Criteria andIsOccupancyGreaterThanOrEqualTo(String value) {
            addCriterion("is_occupancy >=", value, "isOccupancy");
            return (Criteria) this;
        }

        public Criteria andIsOccupancyLessThan(String value) {
            addCriterion("is_occupancy <", value, "isOccupancy");
            return (Criteria) this;
        }

        public Criteria andIsOccupancyLessThanOrEqualTo(String value) {
            addCriterion("is_occupancy <=", value, "isOccupancy");
            return (Criteria) this;
        }

        public Criteria andIsOccupancyLike(String value) {
            addCriterion("is_occupancy like", value, "isOccupancy");
            return (Criteria) this;
        }

        public Criteria andIsOccupancyNotLike(String value) {
            addCriterion("is_occupancy not like", value, "isOccupancy");
            return (Criteria) this;
        }

        public Criteria andIsOccupancyIn(List<String> values) {
            addCriterion("is_occupancy in", values, "isOccupancy");
            return (Criteria) this;
        }

        public Criteria andIsOccupancyNotIn(List<String> values) {
            addCriterion("is_occupancy not in", values, "isOccupancy");
            return (Criteria) this;
        }

        public Criteria andIsOccupancyBetween(String value1, String value2) {
            addCriterion("is_occupancy between", value1, value2, "isOccupancy");
            return (Criteria) this;
        }

        public Criteria andIsOccupancyNotBetween(String value1, String value2) {
            addCriterion("is_occupancy not between", value1, value2, "isOccupancy");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andZsztIsNull() {
            addCriterion("zszt is null");
            return (Criteria) this;
        }

        public Criteria andZsztIsNotNull() {
            addCriterion("zszt is not null");
            return (Criteria) this;
        }

        public Criteria andZsztEqualTo(String value) {
            addCriterion("zszt =", value, "zszt");
            return (Criteria) this;
        }

        public Criteria andZsztNotEqualTo(String value) {
            addCriterion("zszt <>", value, "zszt");
            return (Criteria) this;
        }

        public Criteria andZsztGreaterThan(String value) {
            addCriterion("zszt >", value, "zszt");
            return (Criteria) this;
        }

        public Criteria andZsztGreaterThanOrEqualTo(String value) {
            addCriterion("zszt >=", value, "zszt");
            return (Criteria) this;
        }

        public Criteria andZsztLessThan(String value) {
            addCriterion("zszt <", value, "zszt");
            return (Criteria) this;
        }

        public Criteria andZsztLessThanOrEqualTo(String value) {
            addCriterion("zszt <=", value, "zszt");
            return (Criteria) this;
        }

        public Criteria andZsztLike(String value) {
            addCriterion("zszt like", value, "zszt");
            return (Criteria) this;
        }

        public Criteria andZsztNotLike(String value) {
            addCriterion("zszt not like", value, "zszt");
            return (Criteria) this;
        }

        public Criteria andZsztIn(List<String> values) {
            addCriterion("zszt in", values, "zszt");
            return (Criteria) this;
        }

        public Criteria andZsztNotIn(List<String> values) {
            addCriterion("zszt not in", values, "zszt");
            return (Criteria) this;
        }

        public Criteria andZsztBetween(String value1, String value2) {
            addCriterion("zszt between", value1, value2, "zszt");
            return (Criteria) this;
        }

        public Criteria andZsztNotBetween(String value1, String value2) {
            addCriterion("zszt not between", value1, value2, "zszt");
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