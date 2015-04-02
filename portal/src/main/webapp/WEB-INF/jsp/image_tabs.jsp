<%--
 - Copyright (c) 2015 Memorial Sloan-Kettering Cancer Center.
 -
 - This library is distributed in the hope that it will be useful, but WITHOUT
 - ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY OR FITNESS
 - FOR A PARTICULAR PURPOSE. The software and documentation provided hereunder
 - is on an "as is" basis, and Memorial Sloan-Kettering Cancer Center has no
 - obligations to provide maintenance, support, updates, enhancements or
 - modifications. In no event shall Memorial Sloan-Kettering Cancer Center be
 - liable to any party for direct, indirect, special, incidental or
 - consequential damages, including lost profits, arising out of the use of this
 - software and its documentation, even if Memorial Sloan-Kettering Cancer
 - Center has been advised of the possibility of such damage.
 --%>

<%--
 - This file is part of cBioPortal.
 -
 - cBioPortal is free software: you can redistribute it and/or modify
 - it under the terms of the GNU Affero General Public License as
 - published by the Free Software Foundation, either version 3 of the
 - License.
 -
 - This program is distributed in the hope that it will be useful,
 - but WITHOUT ANY WARRANTY; without even the implied warranty of
 - MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 - GNU Affero General Public License for more details.
 -
 - You should have received a copy of the GNU Affero General Public License
 - along with this program.  If not, see <http://www.gnu.org/licenses/>.
--%>

<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.mskcc.cbio.portal.servlet.QueryBuilder" %>
<%@ page import="org.mskcc.cbio.portal.model.GeneticProfile" %>
<%@ page import="org.mskcc.cbio.portal.model.GeneticAlterationType" %>
<%
      String imageDataTypes[] = { "PROTEIN_LEVEL:Protein Level", "PHOSPHORYLATION:Phosphorylation" }; 
      for( String imageDataTypeAndDesc : imageDataTypes ){
         String fields[] = imageDataTypeAndDesc.split( ":" );
         String imageDataType = fields[0];
         String desc = fields[1];
         
         // an image Tab Should Only Appear if a User Selects the Data Type in Step 2
         // use profileList
         GeneticAlterationType theGeneticAlterationType =
                 GeneticAlterationType.getType( imageDataType );

         for(GeneticProfile theGeneticProfile : profileList){
            if( theGeneticProfile.getGeneticAlterationType() == theGeneticAlterationType &&
                     geneticProfileIdSet.contains( theGeneticProfile.getStableId())){
               out.println("<li class=\"ui-state-default ui-corner-top\"><a href='#" + imageDataType + "'>" + desc + "</a></li>");
            }
         }
      }
%>
      