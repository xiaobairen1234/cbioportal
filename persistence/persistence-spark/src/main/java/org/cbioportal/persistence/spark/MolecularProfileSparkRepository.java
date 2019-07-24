package org.cbioportal.persistence.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.cbioportal.model.MolecularProfile;
import org.cbioportal.model.meta.BaseMeta;
import org.cbioportal.persistence.MolecularProfileRepository;
import org.cbioportal.persistence.spark.util.ParquetConstants;
import org.cbioportal.persistence.spark.util.ParquetLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("molecularProfileSparkRepository")
public class MolecularProfileSparkRepository implements MolecularProfileRepository {

    @Autowired
    private SparkSession spark;

    @Autowired
    private ParquetLoader parquetLoader;
    
    @Override
    public List<MolecularProfile> getAllMolecularProfiles(String projection, Integer pageSize, Integer pageNumber,
                                                          String sortBy, String direction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BaseMeta getMetaMolecularProfiles() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MolecularProfile getMolecularProfile(String molecularProfileId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MolecularProfile> getMolecularProfiles(List<String> molecularProfileIds, String projection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BaseMeta getMetaMolecularProfiles(List<String> molecularProfileIds) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MolecularProfile> getAllMolecularProfilesInStudy(String studyId, String projection, Integer pageSize,
                                                                 Integer pageNumber, String sortBy, String direction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BaseMeta getMetaMolecularProfilesInStudy(String studyId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MolecularProfile> getMolecularProfilesInStudies(List<String> studyIds, String projection) {
        Dataset<Row> meta = parquetLoader.loadStudyFiles(
            spark, new HashSet<>(studyIds), ParquetConstants.META, false);
            
        meta = meta.select("cancer_study_identifier", "genetic_alteration_type", "datatype",
            "stable_id", "show_profile_in_analysis_tab", "profile_name", "profile_description");
        meta = meta.na().drop();

        return meta.collectAsList().stream().
            map(r -> mapToMolecularProfile(r)).collect(Collectors.toList());
    }

    @Override
    public BaseMeta getMetaMolecularProfilesInStudies(List<String> studyIds) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MolecularProfile> getMolecularProfilesReferredBy(String referringMolecularProfileId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MolecularProfile> getMolecularProfilesReferringTo(String referredMolecularProfileId) {
        throw new UnsupportedOperationException();
    }
    
    private MolecularProfile mapToMolecularProfile(Row row) {
        MolecularProfile mp = new MolecularProfile();
        mp.setCancerStudyIdentifier(row.getString(0));
        mp.setMolecularAlterationType(MolecularProfile.MolecularAlterationType.valueOf(row.getString(1)));
        mp.setDatatype(row.getString(2));
        mp.setStableId(mp.getCancerStudyIdentifier() + "_" + row.getString(3));
        mp.setShowProfileInAnalysisTab(Boolean.valueOf(row.getString(4)));
        mp.setName(row.getString(5));
        mp.setDescription(row.getString(6));
        return mp;
    }
}
