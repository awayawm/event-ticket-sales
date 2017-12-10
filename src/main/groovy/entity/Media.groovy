package entity

import groovy.transform.Canonical

@Canonical
class Media {
    int id
    String name
    String description
    String url
}
