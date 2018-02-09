package com.oleksii.kuzko.model;

import java.util.Objects;

/**
 * @author The Weather Company, An IBM Business
 */
public class Language {

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Language language = (Language) o;
        return Objects.equals(code, language.code) &&
                Objects.equals(name, language.name);
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("name", name)
                .toString();
    }
}
