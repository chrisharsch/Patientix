package de.teambluebaer.patientix.helper;


/**
 * Copyright 2015 By Authors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Authors:
 * Simon Sauerzapf
 * Maren Dietrich
 * Chris Harsch
 */
public enum TextSize {
    TITEL(95, 115), SUBTITEL(80, 100), TEXT(65, 85);

    public final float normalSize;
    public final float zoomedSize;

    /**
     * Private method to define the textsizes
     *
     * @param normSize float of the normal size of the text
     * @param zoomSize float of the zoomedsize of the text
     */
    private TextSize(float normSize, float zoomSize) {
        normalSize = normSize;
        zoomedSize = zoomSize;
    }
}
