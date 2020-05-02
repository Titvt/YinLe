package com.titvt.yinle.util.jsoff;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class JSOFF {
    private HashMap<String, Object> elements;

    public JSOFF() {
    }

    public JSOFF(String json) {
        parse(json);
    }

    public String getString(String key) {
        return (String) elements.get(key);
    }

    public BigInteger getBigInteger(String key) {
        return (BigInteger) elements.get(key);
    }

    public Double getDouble(String key) {
        return (Double) elements.get(key);
    }

    public Boolean getBoolean(String key) {
        return (Boolean) elements.get(key);
    }

    public JSOFF getJSOFF(String key) {
        return (JSOFF) elements.get(key);
    }

    public ArrayList<String> getStringArray(String key) {
        return (ArrayList<String>) elements.get(key);
    }

    public ArrayList<BigInteger> getBigIntegerArray(String key) {
        return (ArrayList<BigInteger>) elements.get(key);
    }

    public ArrayList<Double> getDoubleArray(String key) {
        return (ArrayList<Double>) elements.get(key);
    }

    public ArrayList<Boolean> getBooleanArray(String key) {
        return (ArrayList<Boolean>) elements.get(key);
    }

    public ArrayList<JSOFF> getJSOFFArray(String key) {
        return (ArrayList<JSOFF>) elements.get(key);
    }

    public ArrayList<ArrayList> getArrayArray(String key) {
        return (ArrayList<ArrayList>) elements.get(key);
    }

    public JSOFF parse(String json) {
        elements = new HashMap<>();
        String target = json.trim();
        int index = 1;
        while (target.indexOf(':', index) != -1) {
            analyse(getKey(target, index), getValue(target, target.indexOf(':', index) + 1));
            index = getOverValue(target, target.indexOf(':', index) + 1);
        }
        return this;
    }

    private String getKey(String json, int index) {
        return json.substring(json.indexOf('"', index) + 1, json.indexOf('"', json.indexOf('"', index) + 1));
    }

    private String getValue(String json, int index) {
        if (json.isEmpty())
            return "";
        while (json.charAt(index) == ' ' || json.charAt(index) == '\t' || json.charAt(index) == '\n')
            index++;
        return json.substring(index, getOverValue(json, index));
    }

    private int getOverValue(String json, int index) {
        if (json.isEmpty())
            return 0;
        while (json.charAt(index) == ' ' || json.charAt(index) == '\t' || json.charAt(index) == '\n')
            index++;
        int brackets = 0, braces = 0;
        boolean quote = false, escape = false;
        while (index < json.length()) {
            switch (json.charAt(index)) {
                case '[':
                    escape = false;
                    if (!quote)
                        brackets++;
                    break;
                case ']':
                    if (brackets == 0 && braces == 0 && !quote)
                        return index;
                    escape = false;
                    if (!quote)
                        brackets--;
                    break;
                case '{':
                    escape = false;
                    if (!quote)
                        braces++;
                    break;
                case '}':
                    if (brackets == 0 && braces == 0 && !quote)
                        return index;
                    escape = false;
                    if (!quote)
                        braces--;
                    break;
                case '"':
                    if (escape)
                        escape = false;
                    else
                        quote = !quote;
                    break;
                case '\\':
                    escape = !escape;
                    break;
                case ' ':
                case ',':
                    if (brackets == 0 && braces == 0 && !quote)
                        return index;
                default:
                    escape = false;
            }
            index++;
        }
        return index;
    }

    private String getType(String value) {
        if (value.equals("[]"))
            return "Empty Array";
        if (value.charAt(0) == '[') {
            int index = 1;
            while (value.charAt(index) == ' ' || value.charAt(index) == '\t' || value.charAt(index) == '\n')
                index++;
            if (value.charAt(index) == '[' || value.charAt(index) == ']')
                return "Array Array";
            if (value.charAt(index) == '{')
                return "Object Array";
            if (value.charAt(index) == '"')
                return "String Array";
            int start = index;
            while (value.charAt(index) != ' ' && value.charAt(index) != ',' && value.charAt(index) != ']')
                index++;
            if (value.substring(start, index).equals("true") || value.substring(start, index).equals("false"))
                return "Boolean Array";
            while (value.charAt(index) != ']')
                index++;
            return value.substring(start, index).indexOf('.') == -1 ? "BigInteger Array" : "Double Array";
        }
        if (value.charAt(0) == '{')
            return "Object";
        if (value.charAt(0) == '"')
            return "String";
        if (value.equals("true") || value.equals("false"))
            return "Boolean";
        return value.indexOf('.') == -1 ? "BigInteger" : "Double";
    }

    private String getValueString(String value) {
        return value.substring(1, value.length() - 1);
    }

    private BigInteger getValueBigInteger(String value) {
        return value.isEmpty() || value.equals("null") ? BigInteger.ZERO : BigInteger.valueOf(Long.parseLong(value));
    }

    private Double getValueDouble(String value) {
        return Double.parseDouble(value);
    }

    private Boolean getValueBoolean(String value) {
        return value.equals("true");
    }

    private JSOFF getValueJSOFF(String value) {
        return new JSOFF(value);
    }

    private void analyse(String key, String value) {
        int start = 0, end = value.length();
        while (value.charAt(start) == ' ' || value.charAt(start) == '\t' || value.charAt(start) == '\n')
            start++;
        while (value.charAt(end - 1) == ' ' || value.charAt(end - 1) == '\t' || value.charAt(end - 1) == '\n')
            end--;
        value = value.substring(start, end);
        switch (getType(value)) {
            case "Object":
                elements.put(key, getValueJSOFF(value));
                break;
            case "String":
                elements.put(key, getValueString(value));
                break;
            case "Boolean":
                elements.put(key, getValueBoolean(value));
                break;
            case "BigInteger":
                elements.put(key, getValueBigInteger(value));
                break;
            case "Double":
                elements.put(key, getValueDouble(value));
                break;
            case "Object Array":
                ArrayList<JSOFF> arrayObject = new ArrayList<>();
                analyseObjectArray(arrayObject, value);
                elements.put(key, arrayObject);
                break;
            case "String Array":
                ArrayList<String> arrayString = new ArrayList<>();
                analyseStringArray(arrayString, value);
                elements.put(key, arrayString);
                break;
            case "Boolean Array":
                ArrayList<Boolean> arrayBoolean = new ArrayList<>();
                analyseBooleanArray(arrayBoolean, value);
                elements.put(key, arrayBoolean);
                break;
            case "BigInteger Array":
                ArrayList<BigInteger> arrayBigInteger = new ArrayList<>();
                analyseBigIntegerArray(arrayBigInteger, value);
                elements.put(key, arrayBigInteger);
                break;
            case "Double Array":
                ArrayList<Double> arrayDouble = new ArrayList<>();
                analyseDoubleArray(arrayDouble, value);
                elements.put(key, arrayDouble);
                break;
            case "Array Array":
                ArrayList<ArrayList> arrayArray = new ArrayList<>();
                analyseArrayArray(arrayArray, value);
                elements.put(key, arrayArray);
                break;
            case "Empty Array":
                ArrayList<ArrayList> emptyArray = new ArrayList<>();
                elements.put(key, emptyArray);
        }
    }

    private void analyseObjectArray(ArrayList<JSOFF> array, String value) {
        ArrayList<String> arrayList = new ArrayList<>();
        int index = 1;
        while (true) {
            arrayList.add(getValue(value, index));
            index = value.indexOf(',', getOverValue(value, index)) + 1;
            if (value.indexOf(',', getOverValue(value, index)) == -1) {
                arrayList.add(getValue(value, index));
                break;
            }
        }
        for (String item : arrayList)
            array.add(getValueJSOFF(item));
    }

    private void analyseStringArray(ArrayList<String> array, String value) {
        ArrayList<String> arrayList = new ArrayList<>();
        int index = 1;
        while (true) {
            arrayList.add(getValue(value, index));
            index = value.indexOf(',', index) + 1;
            if (value.indexOf(',', index) == -1) {
                arrayList.add(getValue(value, index));
                break;
            }
        }
        for (String item : arrayList)
            array.add(getValueString(item));
    }

    private void analyseBooleanArray(ArrayList<Boolean> array, String value) {
        ArrayList<String> arrayList = new ArrayList<>();
        int index = 1;
        while (true) {
            arrayList.add(getValue(value, index));
            index = value.indexOf(',', index) + 1;
            if (value.indexOf(',', index) == -1) {
                arrayList.add(getValue(value, index));
                break;
            }
        }
        for (String item : arrayList)
            array.add(getValueBoolean(item));
    }

    private void analyseBigIntegerArray(ArrayList<BigInteger> array, String value) {
        ArrayList<String> arrayList = new ArrayList<>();
        int index = 1;
        while (true) {
            arrayList.add(getValue(value, index));
            index = value.indexOf(',', index) + 1;
            if (value.indexOf(',', index) == -1) {
                arrayList.add(getValue(value, index));
                break;
            }
        }
        for (String item : arrayList)
            array.add(getValueBigInteger(item));
    }

    private void analyseDoubleArray(ArrayList<Double> array, String value) {
        ArrayList<String> arrayList = new ArrayList<>();
        int index = 1;
        while (true) {
            arrayList.add(getValue(value, index));
            index = value.indexOf(',', index) + 1;
            if (value.indexOf(',', index) == -1) {
                arrayList.add(getValue(value, index));
                break;
            }
        }
        for (String item : arrayList)
            array.add(getValueDouble(item));
    }

    private void analyseArrayArray(ArrayList<ArrayList> array, String value) {
        ArrayList<String> arrayList = new ArrayList<>();
        int index = 1;
        while (true) {
            arrayList.add(getValue(value, index));
            index = value.indexOf(',', getOverValue(value, index)) + 1;
            if (value.indexOf(',', getOverValue(value, index)) == -1) {
                arrayList.add(getValue(value, index));
                break;
            }
        }
        for (String item : arrayList) {
            switch (getType(item)) {
                case "String Array":
                    ArrayList<String> arrayString = new ArrayList<>();
                    analyseStringArray(arrayString, item);
                    array.add(arrayString);
                    break;
                case "Boolean Array":
                    ArrayList<Boolean> arrayBoolean = new ArrayList<>();
                    analyseBooleanArray(arrayBoolean, item);
                    array.add(arrayBoolean);
                    break;
                case "BigInteger Array":
                    ArrayList<BigInteger> arrayBigInteger = new ArrayList<>();
                    analyseBigIntegerArray(arrayBigInteger, item);
                    array.add(arrayBigInteger);
                    break;
                case "Double Array":
                    ArrayList<Double> arrayDouble = new ArrayList<>();
                    analyseDoubleArray(arrayDouble, item);
                    array.add(arrayDouble);
                    break;
                case "Array Array":
                    ArrayList<ArrayList> arrayArray = new ArrayList<>();
                    analyseArrayArray(arrayArray, item);
                    array.add(arrayArray);
                    break;
                case "Empty Array":
                    ArrayList<ArrayList> emptyArray = new ArrayList<>();
                    array.add(emptyArray);
            }
        }
    }
}