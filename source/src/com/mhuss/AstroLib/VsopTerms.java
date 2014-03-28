/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhuss.AstroLib;

/**
 *
 * @author L
 */
public class VsopTerms {
  int rows;          // number of term sets
  VsopSet pTerms[];  // pointer to start of data

  VsopTerms() { rows=0; }
  VsopTerms( int r, VsopSet p[] ) { rows=r; pTerms=p; }
}