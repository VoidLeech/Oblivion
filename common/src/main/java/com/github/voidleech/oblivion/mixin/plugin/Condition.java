package com.github.voidleech.oblivion.mixin.plugin;

import com.github.voidleech.oblivion.services.Services;

public interface Condition {
    boolean shouldLoad();

    class Builder {
        private Condition condition;

        private Builder(Condition condition) {
            this.condition = condition;
        }

        public static Builder always() {
            return new Builder(() -> true);
        }

        public static Builder modLoaded(String modId) {
            return new Builder(new ModLoaded(modId));
        }

        public static Builder onForge() {
            return new Builder(new Forge());
        }

        public static Builder onFabric() {
            return new Builder(new Fabric());
        }

        public Builder not() {
            condition = new Not(condition);
            return this;
        }

        public Builder and(Builder other){
            condition = new And(condition, other.build());
            return this;
        }

        public Condition build() {
            return condition;
        }
    }

    class Not implements Condition {
        private final Condition condition;

        private Not(Condition condition) {
            this.condition = condition;
        }

        @Override
        public boolean shouldLoad() {
            return !condition.shouldLoad();
        }
    }

    class And implements Condition{
        private final Condition condition;
        private final Condition other;

        private And(Condition condition, Condition other) {
            this.condition = condition;
            this.other = other;
        }

        @Override
        public boolean shouldLoad() {
            return condition.shouldLoad() && other.shouldLoad();
        }
    }

    class ModLoaded implements Condition {
        private final String modId;

        private ModLoaded(String modId) {
            this.modId = modId;
        }

        @Override
        public boolean shouldLoad() {
            return Services.PLATFORM.isModEarlyLoaded(modId);
        }
    }

    class Forge implements Condition {
        private Forge() {
        }

        @Override
        public boolean shouldLoad() {
            return Services.PLATFORM.getPlatformName().equals("Forge");
        }
    }

    class Fabric implements Condition {
        private Fabric() {
        }

        @Override
        public boolean shouldLoad() {
            return Services.PLATFORM.getPlatformName().equals("Fabric");
        }
    }
}