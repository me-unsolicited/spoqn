import $ from 'VendorJS/jQuery.min';

export default {
    headers: {
        'Accept': 'application/json',
        'x-Device-Name': 'My HP',
        'x-Device-Hash': '0123456789ABCDEF'
    },

    get: function (options) {
        let deferred = $.Deferred();

        $.ajax({
            url: options.url,
            type: 'GET',
            headers: Object.assign(this.headers, options.headers),
            success: options.success || deferred.resolve,
            error: options.error || deferred.reject
        });

        return deferred.promise();
    },

    post: function (options) {
        let deferred = $.Deferred();

        $.ajax({
            url: options.url,
            data: JSON.stringify(options.data),
            type: 'POST',
            headers: Object.assign(this.headers, options.headers),
            success: options.success || deferred.resolve,
            error: options.error || deferred.reject
        });

        return deferred.promise();
    }
}
