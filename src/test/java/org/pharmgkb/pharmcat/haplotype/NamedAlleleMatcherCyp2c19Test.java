package org.pharmgkb.pharmcat.haplotype;

import java.nio.file.Path;
import java.util.List;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.pharmgkb.common.util.PathUtils;
import org.pharmgkb.pharmcat.haplotype.model.Result;

import static org.pharmgkb.pharmcat.haplotype.NamedAlleleMatcherTest.assertDiplotypePairs;
import static org.pharmgkb.pharmcat.haplotype.NamedAlleleMatcherTest.testMatchNamedAlleles;


/**
 * JUnit test for {@link NamedAlleleMatcher#callDiplotypes(MatchData)}.
 *
 * @author Mark Woon
 */
public class NamedAlleleMatcherCyp2c19Test {
  private Path m_definitionFile;

  @Before
  public void before() throws Exception {
     m_definitionFile =  PathUtils.getPathToResource("org/pharmgkb/pharmcat/haplotype/CYP2C19_translation.json");
  }


  @Test
  public void cyp2c19s1s1() throws Exception {
    // Simple *1/*1 reference test
    Path vcfFile = PathUtils.getPathToResource("org/pharmgkb/pharmcat/haplotype/cyp2c19/s1s1.vcf");
    List<String> expectedMatches = Lists.newArrayList("*1/*1");

    Result result = testMatchNamedAlleles(m_definitionFile, vcfFile);
    assertDiplotypePairs(expectedMatches, result);
  }


  @Test
  public void cyp2c19s1s2() throws Exception {
    // Test simple case of one heterozygous snp
    Path vcfFile = PathUtils.getPathToResource("org/pharmgkb/pharmcat/haplotype/cyp2c19/s1s2.vcf");
    List<String> expectedMatches = Lists.newArrayList("*1/*2");

    Result result = testMatchNamedAlleles(m_definitionFile, vcfFile);
    assertDiplotypePairs(expectedMatches, result);
  }


  @Test
  public void cyp2c19s1s4b() throws Exception {
    // Test *1 *4b. s1s4b-longest wins(not s4as17)
    Path vcfFile = PathUtils.getPathToResource("org/pharmgkb/pharmcat/haplotype/cyp2c19/s1s4b.vcf");
    List<String> expectedMatches = Lists.newArrayList("*1/*4B", "*4A/*17");

    Result result = testMatchNamedAlleles(m_definitionFile, vcfFile);
    assertDiplotypePairs(expectedMatches, result);
  }


  @Test
  public void cyp2c19s1s4bMissingCalls() throws Exception {
    // Test *1 *4b - but with some of the calls deleted from the vcf.  As requested by Michelle.
    Path vcfFile = PathUtils.getPathToResource("org/pharmgkb/pharmcat/haplotype/cyp2c19/s1s4bMissing.vcf");
    List<String> expectedMatches = Lists.newArrayList("*1/*4B", "*4A/*17");

    Result result = testMatchNamedAlleles(m_definitionFile, vcfFile);
    assertDiplotypePairs(expectedMatches, result);
  }


  @Test
  public void cyp2c19s1s17s1s4bMissingCalls() throws Exception {
    // Test *1 *4b - but with 94762706	rs28399504	A	G	.	PASS	star-4a-4b	GT	./. to test partial call
    Path vcfFile = PathUtils.getPathToResource("org/pharmgkb/pharmcat/haplotype/cyp2c19/s1s17s1s4bmissing.vcf");
    List<String> expectedMatches = Lists.newArrayList("*1/*17", "*1/*4B");

    Result result = testMatchNamedAlleles(m_definitionFile, vcfFile);
    assertDiplotypePairs(expectedMatches, result);
  }


  @Test
  public void cyp2c19s1s17s1s4bMissingMoreCalls() throws Exception {
    // Test *1 *4b - but many calls deleted from the vcf.

    /*
    TODO - returned results are correct.  In the html report *4A and *15 are reported as excluded. However
    as far as I can tell all positions for *28 are also excluded, so why doesn't his make the list? Presume
    this is becuase it's multi position, so similar to *1, but may be worth discussing.

     */
    Path vcfFile = PathUtils.getPathToResource("org/pharmgkb/pharmcat/haplotype/cyp2c19/s1s17s1s4bmissingmore.vcf");
    List<String> expectedMatches = Lists.newArrayList("*1/*17", "*1/*4B");

    Result result = testMatchNamedAlleles(m_definitionFile, vcfFile);
    assertDiplotypePairs(expectedMatches, result);
  }


  @Test
  public void cyp2c19s1s28() throws Exception {
    // Test *1 *28. The longest possible match should win.
    Path vcfFile = PathUtils.getPathToResource("org/pharmgkb/pharmcat/haplotype/cyp2c19/s1s28.vcf");
    List<String> expectedMatches = Lists.newArrayList("*1/*28");

    Result result = testMatchNamedAlleles(m_definitionFile, vcfFile);
    assertDiplotypePairs(expectedMatches, result);
  }


  @Test
  public void cyp2c19s2s2() throws Exception {
    // Test simple case of one homozygous snp
    Path vcfFile = PathUtils.getPathToResource("org/pharmgkb/pharmcat/haplotype/cyp2c19/s2s2.vcf");
    List<String> expectedMatches = Lists.newArrayList("*2/*2");

    Result result = testMatchNamedAlleles(m_definitionFile, vcfFile);
    assertDiplotypePairs(expectedMatches, result);
  }


  @Test
  public void cyp2c19s2s3() throws Exception {
    // Test case of two snps - *2 and *3
    Path vcfFile = PathUtils.getPathToResource("org/pharmgkb/pharmcat/haplotype/cyp2c19/s2s3.vcf");
    List<String> expectedMatches = Lists.newArrayList("*2/*3");

    Result result = testMatchNamedAlleles(m_definitionFile, vcfFile);
    assertDiplotypePairs(expectedMatches, result);
  }


  @Test
  public void cyp2c19s4as4b() throws Exception {
    // Test *4a *4b. het and homo rsids
    Path vcfFile = PathUtils.getPathToResource("org/pharmgkb/pharmcat/haplotype/cyp2c19/s4as4b.vcf");
    List<String> expectedMatches = Lists.newArrayList("*4A/*4B");

    Result result = testMatchNamedAlleles(m_definitionFile, vcfFile);
    assertDiplotypePairs(expectedMatches, result);
  }


  @Test
  public void cyp2c19s4bs17() throws Exception {
    // Test *4b/*17
    Path vcfFile = PathUtils.getPathToResource("org/pharmgkb/pharmcat/haplotype/cyp2c19/s4bs17.vcf");
    List<String> expectedMatches = Lists.newArrayList("*4B/*17");

    Result result = testMatchNamedAlleles(m_definitionFile, vcfFile);
    assertDiplotypePairs(expectedMatches, result);
  }


  @Test
  public void cyp2c19s15s28() throws Exception {
    // Test *15 *28. The shared position is homo
    Path vcfFile = PathUtils.getPathToResource("org/pharmgkb/pharmcat/haplotype/cyp2c19/s15s28.vcf");
    List<String> expectedMatches = Lists.newArrayList("*15/*28");

    Result result = testMatchNamedAlleles(m_definitionFile, vcfFile);
    assertDiplotypePairs(expectedMatches, result);
  }


  @Test
  public void cyp2c19sUnks17() throws Exception {
    // Test *Unk/*17 - only one haplotype matches, so no diploid match
    Path vcfFile = PathUtils.getPathToResource("org/pharmgkb/pharmcat/haplotype/cyp2c19/sUnks17.vcf");
    // no matches
    List<String> expectedMatches = Lists.newArrayList();

    Result result = testMatchNamedAlleles(m_definitionFile, vcfFile);
    assertDiplotypePairs(expectedMatches, result);
  }
}
